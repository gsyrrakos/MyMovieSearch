package com.giorgosyrr.mymoviesearch.di


import com.giorgosyrr.mymoviesearch.basicfunctionfragment.ListBoardContract
import com.giorgosyrr.mymoviesearch.basicfunctionfragment.ListBoardFragment
import com.giorgosyrr.mymoviesearch.basicfunctionfragment.ListBoardPresenter
import com.giorgosyrr.mymoviesearch.data.repository.DataRepository
import com.giorgosyrr.mymoviesearch.data.repository.DataRepositoryImpl
import com.giorgosyrr.mymoviesearch.di.custom_annotation.PerFragment

import dagger.Module
import dagger.Provides

@Module
class FragmentModule(converterActivity: ListBoardFragment) {
    private val listBoardFragment: ListBoardFragment = converterActivity

    @Provides
    @PerFragment
    fun provideView(): ListBoardContract.View {
        return listBoardFragment
    }

    @Provides
    @PerFragment
    fun providePresenter(presenter: ListBoardPresenter?): ListBoardContract.Presenter? {
        return presenter
    }
    @Provides
    @PerFragment
    fun provideLoginInteractor(dataManager: DataRepositoryImpl): DataRepository {
        return dataManager
    }

}