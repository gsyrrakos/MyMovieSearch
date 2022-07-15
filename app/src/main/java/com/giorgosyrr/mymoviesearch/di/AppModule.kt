package com.giorgosyrr.mymoviesearch.di

import javax.inject.Singleton
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context.applicationContext
    }
}