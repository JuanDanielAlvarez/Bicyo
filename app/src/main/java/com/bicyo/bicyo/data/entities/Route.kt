package com.bicyo.bicyo.data.entities

import com.google.android.gms.maps.model.LatLng

data class Route(
    var id:Int,
    var likeCount:Int,
    val name:String,
    val distance:Float,
    val author:User,
    val group:CyclingGroup?,
    val points:MutableList<LatLng>
)
