package com.dzakdzaks.ojekapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakdzaks.ojekapp.data.remote.BaseResponse
import com.dzakdzaks.ojekapp.event.MutableStateEventManager
import com.dzakdzaks.ojekapp.event.StateEvent
import com.dzakdzaks.ojekapp.event.StateEventManager
import com.dzakdzaks.ojekapp.event.StateEventSubscriber
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import retrofit2.Response

typealias FlowState<T> = Flow<StateEvent<T>>

fun <T> default() = MutableStateEventManager<T>()

fun <T> ViewModel.convertEventToSubscriber(
    eventManager: StateEventManager<T>,
    subscriber: StateEventSubscriber<T>
) {
    eventManager.subscribe(
        scope = viewModelScope,
        onIdle = subscriber::onIdle,
        onLoading = subscriber::onLoading,
        onFailure = subscriber::onFailure,
        onSuccess = subscriber::onSuccess
    )
}

fun <T, R> Response<T>.asFlowStateEvent(mapper: (T) -> R): FlowState<R> {
    return flow {
        emit(StateEvent.Loading())

        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                val mapperData = mapper.invoke(body)
                StateEvent.Success(mapperData)
            } else {
                val type = object : TypeToken<BaseResponse<Any>>() {}.type
                val errorResponse: BaseResponse<Any> = Gson().fromJson(errorBody()?.charStream(), type)
                val exception = Throwable(errorResponse.message)
                StateEvent.Failure(exception)
            }
        } catch (e: Throwable) {
            StateEvent.Failure(e)
        }

        emit(emitData)
    }
}

@OptIn(FlowPreview::class)
fun <T, R> FlowState<T>.flatMap(transform: (T) -> R): FlowState<R> {
    return this.flatMapMerge {
        flow {
            when (it) {
                is StateEvent.Loading -> emit(StateEvent.Loading())
                is StateEvent.Idle -> emit(StateEvent.Idle())
                is StateEvent.Failure -> emit(StateEvent.Failure(it.exception))
                is StateEvent.Success -> {
                    val data = transform.invoke(it.data)
                    emit(StateEvent.Success(data))
                }
            }
        }
    }
}