package com.bicyo.bicyo.entities

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
