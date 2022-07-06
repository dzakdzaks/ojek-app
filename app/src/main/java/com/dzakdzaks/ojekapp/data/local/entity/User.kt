package com.dzakdzaks.ojekapp.data.local.entity

data class User(
    val token: String = "",
    val id: String = "",
    val username: String = "",
    val role: Role = Role(),
    val createdDate: String = "",
    val updatedDate: String = "",
)