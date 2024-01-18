package com.example.medicalsupply.core

import android.app.Application
import android.os.StrictMode
import com.example.medicalsupply.data.room.ProductDatabase
import dagger.hilt.android.HiltAndroidApp


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ProductDatabase.initDatabase(this)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build()
        )

    }
}