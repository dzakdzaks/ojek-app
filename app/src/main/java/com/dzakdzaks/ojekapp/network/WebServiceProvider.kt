package com.dzakdzaks.ojekapp.network

import org.koin.core.annotation.Single

@Single
class WebServiceProvider {

    fun get(): WebService {
        return RetrofitConfig.build()
    }

}