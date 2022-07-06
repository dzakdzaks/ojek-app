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
class DriverNetworkSource(
    private val webServiceProvider: WebServiceProvider
) {

    suspend fun registerDriver(request: UserRegisterLoginRequest): FlowState<Boolean> {
        return webServiceProvider.get().registerDriver(request).asFlowStateEvent { it.data.orFalse() }
    }

    suspend fun loginDriver(request: UserRegisterLoginRequest): FlowState<User> {
        return webServiceProvider.get().loginDriver(request).asFlowStateEvent { Mapper.mapUserLoginResponseToEntity(it.data) }
    }

    suspend fun getDriver(): FlowState<User> {
        return webServiceProvider.get().getDriver().asFlowStateEvent { Mapper.mapUserResponseToEntity(it.data) }
    }

}