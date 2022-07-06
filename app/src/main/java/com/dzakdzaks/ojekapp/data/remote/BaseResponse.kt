package com.dzakdzaks.ojekapp.data.remote

data class BaseResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T?
)