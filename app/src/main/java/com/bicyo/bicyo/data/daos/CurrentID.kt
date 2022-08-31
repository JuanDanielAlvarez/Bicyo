package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


data class CurrentID(
    var CyclingGroup: Int=0,
    var Route: Int=0,
    var User: Int=0,
)


class CurrentIDDAO {
    val db = Firebase.firestore
    val doc = db.collection("CurrentID")
        .document("r2UXX66eYzC9WObfyfMW")

    private fun get(): CurrentID? {
        var currentID: CurrentID? = null

        doc.get().addOnSuccessListener { documentSnapshot ->
            currentID = documentSnapshot.toObject<CurrentID>()
        }

        return currentID
    }

    fun getCurrentCyclingGroupID(): Int {
        val currentID = get()
        if (currentID == null) {
            return 0
        }
        currentID.CyclingGroup++
        doc.set(currentID)
        return currentID.CyclingGroup
    }


    fun getCurrentUserID(): Int {
        val currentID = get()
        if (currentID == null) {
            return 0
        }
        currentID.User++
        doc.set(currentID)
        return currentID.User
    }

    fun getCurrentRouteID(): Int {
        val currentID = get()
        if (currentID == null) {
            return 0
        }
        currentID.Route++
        doc.set(currentID)
        return currentID.Route
    }

}