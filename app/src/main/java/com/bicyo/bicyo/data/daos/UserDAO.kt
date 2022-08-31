package com.bicyo.bicyo.data.daos

import android.content.ContentValues.TAG
import android.util.Log
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import okhttp3.internal.wait
import java.util.*

data class UserUtil(
    var id:Int = 0,
    var email:String = "",
    var name:String = "",
    var description:String = "",
    val profilePictureUrl:String = "",
    var publishedRoutes:Int = 0,
    var numberOfGroups:Int = 0,
    val routeIds: List<Int> = listOf(),
    val cyclingGroupIds: List<Int> = listOf(),
)

class UserDAO() : DAO<User> {

    val db = Firebase.firestore

    private fun convertToUtil(user: User):UserUtil{
        return UserUtil(
            user.id,
            user.email,
            user.name,
            user.description,
            user.profilePictureUrl,
            user.publishedRoutes,
            user.numberOfGroups,
            user.routes.map { it.id },
            user.cyclingGroups.map { it.id },
        )
    }

    private fun convertToUser(userUtil: UserUtil,onSuccess: (User?) -> Unit):Unit {

        db.collection("Route")
            .whereEqualTo("author", userUtil.id)
            .get()
            .addOnSuccessListener { documents ->

                var routesList = mutableListOf<Route>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val routeUtil = document.toObject<RouteUtil>()
                    val route = Route(
                        routeUtil.id,
                        routeUtil.likeCount,
                        routeUtil.name,
                        routeUtil.distance,
                        User(
                            userUtil.id,
                            userUtil.email,
                            userUtil.name,
                            userUtil.description,
                            userUtil.profilePictureUrl,
                            userUtil.publishedRoutes,
                            userUtil.numberOfGroups,
                            listOf(),
                            listOf()
                        ),
                        null,
                        routeUtil.points.map { LatLng(it[0], it[1]) }.toMutableList()
                    )
                    routesList.add(route)
                }
                val user = User(
                    userUtil.id,
                    userUtil.email,
                    userUtil.name,
                    userUtil.description,
                    userUtil.profilePictureUrl,
                    userUtil.publishedRoutes,
                    userUtil.numberOfGroups,
                    routesList,
                    listOf()
                )
                onSuccess(user)
            }
    }

    override fun get(id: Int, onSuccess: (User?) -> Unit) {

        db.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                var userUtil:UserUtil? = null
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    userUtil = document.toObject<UserUtil>()
                }
                if (userUtil != null) {
                    convertToUser(userUtil){user ->
                        onSuccess(user)
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    override fun save(user: User): Boolean {
        var saved = true
        user.id = CurrentIDDAO().getCurrentUserID()

        val userUtil = convertToUtil(user)

        db.collection("User")
            .add(userUtil)
            .addOnSuccessListener { documentReference ->
                saved = true
            }
            .addOnFailureListener { e ->
                saved = false
            }
        return saved
    }

    override fun update(i: Int, user: User): Boolean {
        var saved = true
        var docId = ""

        val userUtil = convertToUtil(user)

        db.collection("User")
            .whereEqualTo("id", i)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }

                db.collection("User")
                    .document(docId)
                    .set(userUtil)
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

        var docId:String? = null
        db.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }
            }
        if(docId == null) return false

        var deleted = true

        db.collection("User")
            .document(docId!!)
            .delete()
            .addOnFailureListener { e ->
                deleted = false
            }

        return deleted
    }

}