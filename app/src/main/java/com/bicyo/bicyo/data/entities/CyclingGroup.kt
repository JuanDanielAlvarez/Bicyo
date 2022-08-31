package com.bicyo.bicyo.data.entities

data class CyclingGroup(
    var id:Int,
    var name: String,
    var members: List<User>,
    var routes: List<Route>,
)
