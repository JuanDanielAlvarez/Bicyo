package com.bicyo.bicyo.entities

data class Route(
    val id:Int,
    var likeCount:Int,
    val name:String,
    val distance:Float,
    val author:User,
    val group:CyclingGroup?
)
