package com.dzakdzaks.ojekapp.event

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking

open class MutableStateEventManager<T> : StateEventManager<T>(), FlowCollector<StateEvent<T>> {

    private val flowEvent: MutableStateFlow<StateEvent<T>> = MutableStateFlow(StateEvent.Idle())

    override var errorDispatcher: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            runBlocking {
                val errorState = StateEvent.Failure<T>(throwable)
                flowEvent.emit(errorState)
            }
        }
    override var listener: StateEvent<T>.(StateEventManager<T>) -> Unit = {}

    override fun subscribe(
        scope: CoroutineScope,
        onIdle: () -> Unit,
        onLoading: () -> Unit,
        onFailure: (Throwable) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        createScope(scope).launch {
            flowEvent.collect {
                value = it
                listener(it, this@MutableStateEventManager)
                when (it) {
                    is StateEvent.Idle -> onIdle.invoke()
                    is StateEvent.Loading -> onLoading.invoke()
                    is StateEvent.Failure -> onFailure.invoke(it.exception)
                    is StateEvent.Success -> onSuccess.invoke(it.data)
                }
            }
        }
    }

    override fun <R> map(mapper: (T) -> R): StateEventManager<R> =
        MapperStateEventManager(this, mapper)

    override fun invoke(): T? = (value as? StateEvent.Success<T>)?.data

    override fun createScope(scope: CoroutineScope): CoroutineScope = scope + errorDispatcher

    inner class MapperStateEventManager<R>(
        private val stateEventManager: StateEventManager<T>,
        private val mapper: (T) -> R
    ) : MutableStateEventManager<R>() {
        override fun subscribe(
            scope: CoroutineScope,
            onIdle: () -> Unit,
            onLoading: () -> Unit,
            onFailure: (throwable: Throwable) -> Unit,
            onSuccess: (R) -> Unit
        ) {
            stateEventManager.listener = {
                when (this) {
                    is StateEvent.Idle -> {
                        value = StateEvent.Idle()
                        onIdle.invoke()
                    }
                    is StateEvent.Loading -> {
                        value = StateEvent.Loading()
                        onLoading.invoke()
                    }
                    is StateEvent.Failure -> {
                        value = StateEvent.Failure(this.exception)
                        onFailure.invoke(this.exception)
                    }
                    is StateEvent.Success -> {
                        val mapData = mapper.invoke(this.data)
                        value = StateEvent.Success(mapData)
                        onSuccess.invoke(mapData)
                    }
                }

                listener.invoke(value, this@MapperStateEventManager)
            }
        }
    }

    override suspend fun emit(value: StateEvent<T>) {
        flowEvent.emit(value)
    }
}