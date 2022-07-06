package com.dzakdzaks.ojekapp.repository.customer

import com.dzakdzaks.ojekapp.data.local.DataPreferences
import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.event.StateEventManager
import com.dzakdzaks.ojekapp.network.source.CustomerNetworkSource
import com.dzakdzaks.ojekapp.utils.default
import org.koin.core.annotation.Single

@Single
class CustomerRepositoryImpl(
    private val customerNetworkSource: CustomerNetworkSource
): CustomerRepository {

    private val _registerStateEventManager = default<Boolean>()
    override val registerStateEventManager: StateEventManager<Boolean>
        get() = _registerStateEventManager

    private val _loginStateEventManager = default<User>()
    override val loginStateEventManager: StateEventManager<User>
        get() = _loginStateEventManager

    private val _customerStateEventManager = default<User>()
    override val customerStateEventManager: StateEventManager<User>
        get() = _customerStateEventManager


    override suspend fun register(request: UserRegisterLoginRequest) {
        customerNetworkSource.registerCustomer(request).collect(_registerStateEventManager)
    }

    override suspend fun login(request: UserRegisterLoginRequest) {
        customerNetworkSource.loginCustomer(request).collect(_loginStateEventManager)
    }

    override suspend fun getCustomer() {
        customerNetworkSource.getCustomer().collect(_customerStateEventManager)
    }

    override fun saveToken(newToken: String) {
        DataPreferences.get.token = newToken
    }
}