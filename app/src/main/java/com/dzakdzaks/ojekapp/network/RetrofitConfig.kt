package com.dzakdzaks.ojekapp.network

import com.dzakdzaks.ojekapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitConfig {

    private const val BASE_URL = "https://ojek-api.herokuapp.com"

    fun build(): WebService {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
        }
        httpClient.addInterceptor(AuthInterceptor())

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(WebService::class.java)
    }

    object EndPoint {

        private const val api = "/api"
        private const val V1 = "/v1"

        const val CUSTOMER_REGISTER = "$api$V1/customer/register"
        const val CUSTOMER_LOGIN = "$api$V1/customer/login"
        const val GET_CUSTOMER = "$api$V1/customer"

        const val DRIVER_REGISTER = "$api$V1/customer/register"
        const val DRIVER_LOGIN = "$api$V1/customer/login"
        const val GET_DRIVER = "$api$V1/customer"

    }

}