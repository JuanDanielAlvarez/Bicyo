package com.bicyo.bicyo.data.daos

import com.bicyo.bicyo.data.entities.CyclingGroup

class CyclingGroupDAO() : DAO<CyclingGroup> {
    override fun get(id: Int): CyclingGroup? {
        //Obtener uno en base al id del grupo
        return CyclingGroup(1,"Grupo 1", listOf(), listOf())
    }

    override fun save(t: CyclingGroup):Boolean {
        return true
    }

    override fun update(i: Int, t: CyclingGroup):Boolean {
        return true
    }

    override fun delete(i: Int): CyclingGroup? {
        return CyclingGroup(1,"Grupo 1", listOf(), listOf())
    }

    fun getAllForUser(userId: Int): List<CyclingGroup> {
        //Obtener todos los grupos en base al id del usuario
        val cyclingGroups = listOf(
            CyclingGroup(2,"Ggupo 2", listOf(), listOf()),
            CyclingGroup(3,"Ggupo 3", listOf(), listOf())
        )
        return cyclingGroups
    }

}