package com.dzakdzaks.ojekapp.network.source

import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.mapper.Mapper
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.network.WebServiceProvider
import com.dzakdzaks.ojekapp.utils.FlowState
import com.dzakdzaks.ojekapp.utils.asFlowStateEvent
import com.dzakdzaks.ojekapp.utils.orFalse
import org.koin.core.annotation.Single

@Single
class CustomerNetworkSource(
    private val webServiceProvider: WebServiceProvider
) {

    suspend fun registerCustomer(request: UserRegisterLoginRequest): FlowState<Boolean> {
        return webServiceProvider.get().registerCustomer(request).asFlowStateEvent { it.data.orFalse() }
    }

    suspend fun loginCustomer(request: UserRegisterLoginRequest): FlowState<User> {
        return webServiceProvider.get().loginCustomer(request).asFlowStateEvent { Mapper.mapUserLoginResponseToEntity(it.data) }
    }

    suspend fun getCustomer(): FlowState<User> {
        return webServiceProvider.get().getCustomer().asFlowStateEvent { Mapper.mapUserResponseToEntity(it.data) }
    }

}