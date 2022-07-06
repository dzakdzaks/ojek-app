package com.dzakdzaks.ojekapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.event.StateEventSubscriber
import com.dzakdzaks.ojekapp.repository.customer.CustomerRepository
import com.dzakdzaks.ojekapp.repository.driver.DriverRepository
import com.dzakdzaks.ojekapp.utils.convertEventToSubscriber
import kotlinx.coroutines.launch
import org.koin.core.annotation.Scope

@Scope(MainActivity::class)
class MainViewModel(
    private val customerRepository: CustomerRepository,
    private val driverRepository: DriverRepository
) : ViewModel() {

    // Customer Variables
    private val registerCustomerManager = customerRepository.registerStateEventManager
    private val registerCustomerScope = registerCustomerManager.createScope(viewModelScope)

    private val loginCustomerManager = customerRepository.loginStateEventManager
    private val loginCustomerScope = loginCustomerManager.createScope(viewModelScope)

    private val getCustomerManager = customerRepository.customerStateEventManager
    private val getCustomerScope = getCustomerManager.createScope(viewModelScope)

    // Driver Variables
    private val registerDriverManager = driverRepository.registerStateEventManager
    private val registerDriverScope = registerDriverManager.createScope(viewModelScope)

    private val loginDriverManager = driverRepository.loginStateEventManager
    private val loginDriverScope = loginDriverManager.createScope(viewModelScope)

    private val getDriverManager = driverRepository.driverStateEventManager
    private val getDriverScope = getDriverManager.createScope(viewModelScope)


    // Customer Functions
    fun subscribeRegisterCustomer(subscriber: StateEventSubscriber<Boolean>) {
        convertEventToSubscriber(registerCustomerManager, subscriber)
    }

    fun registerCustomer(username: String, password: String) = registerCustomerScope.launch {
        customerRepository.register(UserRegisterLoginRequest(username, password))
    }

    fun subscribeLoginCustomer(subscriber: StateEventSubscriber<User>) {
        convertEventToSubscriber(loginCustomerManager, subscriber)
    }

    fun loginCustomer(username: String, password: String) = loginCustomerScope.launch {
        customerRepository.login(UserRegisterLoginRequest(username, password))
    }

    fun subscribeGetCustomer(subscriber: StateEventSubscriber<User>) {
        convertEventToSubscriber(getCustomerManager, subscriber)
    }

    fun getCustomer() = getCustomerScope.launch {
        customerRepository.getCustomer()
    }

    // Driver Functions
    fun subscribeRegisterDriver(subscriber: StateEventSubscriber<Boolean>) {
        convertEventToSubscriber(registerDriverManager, subscriber)
    }

    fun registerDriver(username: String, password: String) = registerDriverScope.launch {
        driverRepository.register(UserRegisterLoginRequest(username, password))
    }

    fun subscribeLoginDriver(subscriber: StateEventSubscriber<User>) {
        convertEventToSubscriber(loginDriverManager, subscriber)
    }

    fun loginDriver(username: String, password: String) = loginDriverScope.launch {
        driverRepository.login(UserRegisterLoginRequest(username, password))
    }

    fun subscribeGetDriver(subscriber: StateEventSubscriber<User>) {
        convertEventToSubscriber(getDriverManager, subscriber)
    }

    fun getDriver() = getDriverScope.launch {
        driverRepository.getDriver()
    }


    // Other Functions
    fun saveCustomerToken(newToken: String) {
        customerRepository.saveToken(newToken)
    }

    fun saveDriverToken(newToken: String) {
        driverRepository.saveToken(newToken)
    }

}