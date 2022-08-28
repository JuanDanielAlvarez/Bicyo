package com.bicyo.bicyo.entities

data class CyclingGroup(
    val id:Int,
    val name: String,
    var members: List<User>,
    var routes: List<Route>,
)
