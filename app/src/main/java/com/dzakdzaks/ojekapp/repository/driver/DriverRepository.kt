package com.dzakdzaks.ojekapp.repository.driver

import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.event.StateEventManager

interface DriverRepository {
    val registerStateEventManager: StateEventManager<Boolean>
    val loginStateEventManager: StateEventManager<User>
    val driverStateEventManager: StateEventManager<User>

    suspend fun register(request: UserRegisterLoginRequest)
    suspend fun login(request: UserRegisterLoginRequest)
    suspend fun getDriver()

    fun saveToken(newToken: String)
}