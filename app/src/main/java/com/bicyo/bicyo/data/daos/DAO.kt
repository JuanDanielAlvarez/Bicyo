package com.bicyo.bicyo.data.daos

interface DAO<T> {
    operator fun get(id: Int): T?
    fun save(t: T):Boolean
    fun update(i:Int,t: T):Boolean
    fun delete(i:Int):Boolean
}