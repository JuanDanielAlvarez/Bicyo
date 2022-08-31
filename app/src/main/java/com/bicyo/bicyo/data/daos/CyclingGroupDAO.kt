package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

data class CyclingGroupUtil(
    var id:Int = 0,
    var name: String = "",
    var memberIds: List<Int> = listOf(),
    var routeIds: List<Int> = listOf(),
)



class CyclingGroupDAO() : DAO<CyclingGroup> {

    val db = Firebase.firestore
    private fun convertToUtil(group: CyclingGroup): CyclingGroupUtil {
        return CyclingGroupUtil(
            group.id,
            group.name,
            group.members.map { it.id },
            group.routes.map { it.id },
        )
    }

    private fun convertToGroup(groupUtil: CyclingGroupUtil): CyclingGroup {

        val routeDAO = RouteDAO()
        val tempRoutesList = mutableListOf<Route>()
//        for(routeId in groupUtil.routeIds){
//            routeDAO.get(routeId)?.let { tempRoutesList.add(it) }
//        }

        val userDAO = UserDAO()
        val tempUserList = mutableListOf<User>()
//        for(userId in groupUtil.memberIds){
//            userDAO.get(userId)?.let { tempUserList.add(it) }
//        }

        return CyclingGroup(
            groupUtil.id,
            groupUtil.name,
            tempUserList,
            tempRoutesList,
        )
    }
    override fun get(id: Int, onSuccess: (CyclingGroup?) -> Unit) {
        db.collection("CyclingGroup")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                var groupUtil: CyclingGroupUtil? = null
                for (document in documents) {
                    groupUtil = document.toObject<CyclingGroupUtil>()
                }
                if (groupUtil != null) {
                    onSuccess(convertToGroup(groupUtil))
                }
            }
    }

    override fun save(group: CyclingGroup):Boolean {
        val groupUtil = convertToUtil(group)

        var saved = true
        groupUtil.id = CurrentIDDAO().getCurrentCyclingGroupID()
        db.collection("CyclingGroup")
            .add(groupUtil)
            .addOnSuccessListener { documentReference ->
                saved = true
            }
            .addOnFailureListener { e ->
                saved = false
            }
        return saved
    }

    override fun update(id: Int, group: CyclingGroup):Boolean {
        val groupUtil = convertToUtil(group)
        var saved = true
        var docId = ""
        db.collection("CyclingGroup")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }

                db.collection("CyclingGroup")
                    .document(docId)
                    .set(groupUtil)
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
        db.collection("CyclingGroup")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docId = document.id
                }
            }
        if(docId == null) return false

        var deleted = true

        db.collection("CyclingGroup")
            .document(docId!!)
            .delete()
            .addOnFailureListener { e ->
                deleted = false
            }

        return deleted
    }


}