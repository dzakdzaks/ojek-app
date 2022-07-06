package com.dzakdzaks.ojekapp.repository.customer

import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.event.StateEventManager

interface CustomerRepository {
    val registerStateEventManager: StateEventManager<Boolean>
    val loginStateEventManager: StateEventManager<User>
    val customerStateEventManager: StateEventManager<User>

    suspend fun register(request: UserRegisterLoginRequest)
    suspend fun login(request: UserRegisterLoginRequest)
    suspend fun getCustomer()

    fun saveToken(newToken: String)
}