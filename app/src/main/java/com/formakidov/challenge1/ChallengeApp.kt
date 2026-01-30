package com.formakidov.challenge1

import android.app.Application
import com.formakidov.challenge1.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChallengeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChallengeApp)
            modules(appModule)
        }
    }
}
