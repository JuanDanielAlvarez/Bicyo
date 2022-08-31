package com.bicyo.bicyo.data.daos

import android.content.ContentValues.TAG
import android.util.Log
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
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

    private fun convertToUser(userUtil: UserUtil):User {
        val user = User(
            userUtil.id,
            userUtil.email,
            userUtil.name,
            userUtil.description,
            userUtil.profilePictureUrl,
            userUtil.publishedRoutes,
            userUtil.numberOfGroups,
            listOf(),
            listOf()
        )


        val routeDAO = RouteDAO()
        val tempRoutesList = mutableListOf<Route>()
        for(routeId in userUtil.routeIds){
            routeDAO.get(routeId)?.let { tempRoutesList.add(it) }
        }
        user.routes = tempRoutesList

        val groupDAO = CyclingGroupDAO()
        val tempGroupsList = mutableListOf<CyclingGroup>()
        for(groupId in userUtil.cyclingGroupIds){
            groupDAO.get(groupId)?.let { tempGroupsList.add(it) }
        }
        user.cyclingGroups = tempGroupsList

        return user
    }

    override fun get(id: Int): User? {
        var userUtil:UserUtil? = null
        db.collection("User")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    userUtil = document.toObject<UserUtil>()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return userUtil?.let { convertToUser(it) }
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