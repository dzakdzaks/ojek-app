package com.dzakdzaks.ojekapp.repository.driver

import com.dzakdzaks.ojekapp.data.local.DataPreferences
import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.event.StateEventManager
import com.dzakdzaks.ojekapp.network.source.DriverNetworkSource
import com.dzakdzaks.ojekapp.utils.default
import org.koin.core.annotation.Single

@Single
class DriverRepositoryImpl(
    private val driverNetworkSource: DriverNetworkSource
): DriverRepository {

    private val _registerStateEventManager = default<Boolean>()
    override val registerStateEventManager: StateEventManager<Boolean>
        get() = _registerStateEventManager

    private val _loginStateEventManager = default<User>()
    override val loginStateEventManager: StateEventManager<User>
        get() = _loginStateEventManager

    private val _driverStateEventManager = default<User>()
    override val driverStateEventManager: StateEventManager<User>
        get() = _driverStateEventManager


    override suspend fun register(request: UserRegisterLoginRequest) {
        driverNetworkSource.registerDriver(request).collect(_registerStateEventManager)
    }

    override suspend fun login(request: UserRegisterLoginRequest) {
        driverNetworkSource.loginDriver(request).collect(_loginStateEventManager)
    }

    override suspend fun getDriver() {
        driverNetworkSource.getDriver().collect(_driverStateEventManager)
    }

    override fun saveToken(newToken: String) {
        DataPreferences.get.token = newToken
    }
}