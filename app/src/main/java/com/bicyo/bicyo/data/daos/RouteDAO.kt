package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import java.util.*

class RouteDAO() : DAO<Route> {
    override fun get(id: Int): Route? {
        val user = User(
            1,
            "juan.alvarez@epn.edu.ec",
            "Juan Alvarez",
            "",
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",
            1,
            1,
            listOf(),
            listOf()
        )
        return Route(1, 1, "Ruta 1", 100.0f, user, null, mutableListOf())
    }

    override fun save(t: Route): Boolean {
        return true
    }

    override fun update(i: Int, t: Route): Boolean {
        return true
    }

    override fun delete(i: Int): Route? {
        val user = User(
            1,
            "juan.alvarez@epn.edu.ec",
            "Juan Alvarez",
            "",
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",
            1,
            1,
            listOf(),
            listOf()
        )
        return Route(1, 1, "Ruta 1", 100.0f, user, null, mutableListOf())
    }

    fun getAllForGroup(groupId: Int): List<Route> {
        //Obtener todos los grupos en base al id del usuario
        val user = User(
            1,
            "juan.alvarez@epn.edu.ec",
            "Juan Alvarez",
            "",
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",
            1,
            1,
            listOf(),
            listOf()
        )
        val routes = listOf(
            Route(1, 1, "Ruta 1", 100.0f, user, null, mutableListOf()),
            Route(1, 1, "Ruta 1", 100.0f, user, null, mutableListOf())
        )
        return routes
    }

}