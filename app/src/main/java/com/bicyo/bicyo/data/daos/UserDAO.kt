package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.User
import java.util.*

class UserDAO() : DAO<User> {
    override fun get(id: Long): User? {
        return User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
    }

    override fun save(t: User):Boolean {
        return true
    }

    override fun update(i: Int, t: User):Boolean {
        return true
    }

    override fun delete(i: Int): User? {
        return User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
    }

    override fun getAll(): List<User> {
        val users = listOf(
            User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf()),
            User(2,"daniel.aimacana@epn.edu.ec","Daniel Aimacana","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        )
        return users
    }

}