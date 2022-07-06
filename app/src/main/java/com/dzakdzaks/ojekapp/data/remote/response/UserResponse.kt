package com.dzakdzaks.ojekapp.data.remote.response

data class UserResponse(
    val id: String?,
    val username: String?,
    val role: RoleResponse?,
    val createdDate: String?,
    val updatedDate: String?,
)