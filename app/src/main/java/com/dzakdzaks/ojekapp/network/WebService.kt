package com.dzakdzaks.ojekapp.network

import com.dzakdzaks.ojekapp.data.remote.BaseResponse
import com.dzakdzaks.ojekapp.data.remote.request.UserRegisterLoginRequest
import com.dzakdzaks.ojekapp.data.remote.response.UserLoginResponse
import com.dzakdzaks.ojekapp.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {

    @POST(RetrofitConfig.EndPoint.CUSTOMER_REGISTER)
    suspend fun registerCustomer(
        @Body customerRegisterRequest: UserRegisterLoginRequest
    ): Response<BaseResponse<Boolean>>

    @POST(RetrofitConfig.EndPoint.CUSTOMER_LOGIN)
    suspend fun loginCustomer(
        @Body customerRegisterRequest: UserRegisterLoginRequest
    ): Response<BaseResponse<UserLoginResponse>>

    @GET(RetrofitConfig.EndPoint.GET_CUSTOMER)
    suspend fun getCustomer(): Response<BaseResponse<UserResponse>>

    @POST(RetrofitConfig.EndPoint.DRIVER_REGISTER)
    suspend fun registerDriver(
        @Body customerRegisterRequest: UserRegisterLoginRequest
    ): Response<BaseResponse<Boolean>>

    @POST(RetrofitConfig.EndPoint.DRIVER_LOGIN)
    suspend fun loginDriver(
        @Body customerRegisterRequest: UserRegisterLoginRequest
    ): Response<BaseResponse<UserLoginResponse>>

    @GET(RetrofitConfig.EndPoint.GET_DRIVER)
    suspend fun getDriver(): Response<BaseResponse<UserResponse>>

}