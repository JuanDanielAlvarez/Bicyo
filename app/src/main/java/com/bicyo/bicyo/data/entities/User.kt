package com.bicyo.bicyo.data.entities

data class User(
    var id:Int,
    var email:String,
    var name:String,
    var description:String,
    val profilePictureUrl:String,
    var publishedRoutes:Int,
    var numberOfGroups:Int,
    var routes: List<Route>,
    var cyclingGroups: List<CyclingGroup>,
)
