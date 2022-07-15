package com.giorgosyrr.mymoviesearch


import android.app.Application
import com.giorgosyrr.mymoviesearch.data.remote.RemoteModule
import com.giorgosyrr.mymoviesearch.di.AppComponent
import com.giorgosyrr.mymoviesearch.di.AppModule
import com.giorgosyrr.mymoviesearch.di.DaggerAppComponent


class BaseApplication : Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .remoteModule(RemoteModule())
            .build()
        appComponent!!.inject(this)
    }
}