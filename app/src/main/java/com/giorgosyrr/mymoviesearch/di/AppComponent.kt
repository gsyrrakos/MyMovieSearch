package com.giorgosyrr.mymoviesearch.di

import com.giorgosyrr.mymoviesearch.BaseApplication

import com.giorgosyrr.mymoviesearch.data.remote.RemoteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class])
interface AppComponent {
    fun inject(baseApplication: BaseApplication?)
    fun converterActivitySubcomponent(fragmentModule: FragmentModule?): FragmentSubComponent?
}