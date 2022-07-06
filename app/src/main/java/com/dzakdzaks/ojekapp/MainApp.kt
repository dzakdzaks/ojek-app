package com.dzakdzaks.ojekapp

import android.app.Application
import com.dzakdzaks.ojekapp.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(MainModule().module)
        }
    }

}