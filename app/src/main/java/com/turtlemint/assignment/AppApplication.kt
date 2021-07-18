package com.turtlemint.assignment

import androidx.multidex.MultiDexApplication
import com.turtlemint.assignment.di.databaseModules
import com.turtlemint.assignment.di.remoteModules
import com.turtlemint.assignment.di.repositoryModules
import com.turtlemint.assignment.di.viewModelModules
import org.koin.android.ext.android.startKoin

class AppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Configure the koin dependency injection.
        startKoin(
            this, listOf(
                remoteModules,
                databaseModules,
                repositoryModules,
                viewModelModules
            )
        )
    }
}