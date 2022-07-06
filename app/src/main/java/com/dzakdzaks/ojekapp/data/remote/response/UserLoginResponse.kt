package com.dzakdzaks.ojekapp.data.remote.response

data class UserLoginResponse(
    val token: String?,
    val user: UserResponse?
)