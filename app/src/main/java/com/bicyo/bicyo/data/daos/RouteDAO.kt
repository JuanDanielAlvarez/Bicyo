package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


data class RouteUtil(
    var id: Int = 0,
    var likeCount: Int = 0,
    val name: String = "",
    val distance: Float = 0.0f,
    val authorId: Int = 0,
    val groupId: Int? = null,
    val points: List<List<Double>> = listOf()
)

class RouteDAO() : DAO<Route> {

    val db = Firebase.firestore

    private fun convertToUtil(route: Route): RouteUtil {
        return RouteUtil(
            route.id,
            route.likeCount,
            route.name,
            route.distance,
            route.author.id,
            route.group?.id,
            route.points.map { listOf(it.latitude,it.longitude) }
        )
    }

    private fun convertToRoute(routeUtil: RouteUtil): Route {
        val autor =
            UserDAO().get(routeUtil.authorId) ?: User(0, "", "", "", "", 1, 1, listOf(), listOf())
        val group = routeUtil.groupId?.let { CyclingGroupDAO().get(it) }

        return Route(
            routeUtil.id,
            routeUtil.likeCount,
            routeUtil.name,
            routeUtil.distance,
            autor,
            group,
            routeUtil.points.map { LatLng(it[0],it[1]) }.toMutableList()
        )
    }

    override fun get(id: Int): Route? {
        var routeUtil: RouteUtil? = null
        db.collection("Route")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    routeUtil = document.toObject<RouteUtil>()
                }
            }
        return routeUtil?.let { convertToRoute(it) }
    }

    override fun save(route: Route): Boolean {
        val routeUtil = convertToUtil(route)
        var saved = true
        routeUtil.id = CurrentIDDAO().getCurrentRouteID()
        db.collection("Route")
            .add(routeUtil)
            .addOnSuccessListener { documentReference ->
                saved = true
                val savedRoute = convertToRoute(routeUtil)
                val author = savedRoute.author
                val newList = author.routes.toMutableList()
                newList.add(route)
                author.routes = newList
                author.publishedRoutes++
                UserDAO().update(author.id,author)
            }
            .addOnFailureListener { e ->
                saved = false
            }
        return saved
    }

    override fun update(i: Int, route: Route): Boolean {
        val routeUtil = convertToUtil(route)

        var saved = true
        var docId = ""
        db.collection("Route")
            .whereEqualTo("id", i)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }

                db.collection("Route")
                    .document(docId)
                    .set(routeUtil)
                    .addOnSuccessListener { documentReference ->
                        saved = true
                    }
                    .addOnFailureListener { e ->
                        saved = false
                    }
            }
            .addOnFailureListener { exception ->
                saved = false
            }
        return saved
    }

    override fun delete(id: Int): Boolean {

        var docId: String? = null
        db.collection("Route")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }
            }
        if (docId == null) return false

        var deleted = true

        db.collection("Route")
            .document(docId!!)
            .delete()
            .addOnFailureListener { e ->
                deleted = false
            }

        return deleted
    }
}