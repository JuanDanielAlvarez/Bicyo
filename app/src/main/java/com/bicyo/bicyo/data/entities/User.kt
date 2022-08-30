package com.bicyo.bicyo.data.entities

data class User(
    val id:Int,
    var email:String,
    var name:String,
    var description:String,
    val profilePictureUrl:String,
    var publishedRoutes:Int,
    var numberOfGroups:Int,
    val routes: List<Route>,
    val cyclingGroups: List<CyclingGroup>,
)
