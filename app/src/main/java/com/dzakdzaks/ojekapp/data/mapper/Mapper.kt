package com.dzakdzaks.ojekapp.data.mapper

import com.dzakdzaks.ojekapp.data.local.entity.Role
import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.data.remote.response.RoleResponse
import com.dzakdzaks.ojekapp.data.remote.response.UserLoginResponse
import com.dzakdzaks.ojekapp.data.remote.response.UserResponse

object Mapper {

    fun mapUserLoginResponseToEntity(response: UserLoginResponse?): User {
        val userResponse = response?.user
        return User(
            token = response?.token.orEmpty(),
            id = userResponse?.id.orEmpty(),
            username = userResponse?.username.orEmpty(),
            role = mapRoleResponseToEntity(userResponse?.role),
            createdDate = userResponse?.createdDate.orEmpty(),
            updatedDate = userResponse?.updatedDate.orEmpty(),
        )
    }

    fun mapUserResponseToEntity(response: UserResponse?): User {
        return User(
            id = response?.id.orEmpty(),
            username = response?.username.orEmpty(),
            role = mapRoleResponseToEntity(response?.role),
            createdDate = response?.createdDate.orEmpty(),
            updatedDate = response?.updatedDate.orEmpty(),
        )
    }

    fun mapRoleResponseToEntity(response: RoleResponse?): Role =
        Role(id = response?.id.orEmpty(), name = response?.name.orEmpty())

}