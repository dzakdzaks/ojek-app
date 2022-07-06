package com.dzakdzaks.ojekapp.event

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope

abstract class StateEventManager<T> {

    var value: StateEvent<T> = StateEvent.Idle()
        protected set

    abstract var errorDispatcher: CoroutineExceptionHandler

    abstract var listener: StateEvent<T>.(StateEventManager<T>) -> Unit

    abstract fun subscribe(
        scope: CoroutineScope,
        onIdle: () -> Unit = {},
        onLoading: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {},
    )

    abstract fun <R> map(mapper: (T) -> R): StateEventManager<R>

    abstract fun invoke(): T?

    abstract fun createScope(scope: CoroutineScope): CoroutineScope

}