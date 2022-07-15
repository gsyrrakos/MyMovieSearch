package com.giorgosyrr.mymoviesearch.di



import com.giorgosyrr.mymoviesearch.basicfunctionfragment.ListBoardFragment
import com.giorgosyrr.mymoviesearch.di.FragmentModule
import com.giorgosyrr.mymoviesearch.di.custom_annotation.PerFragment

import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentSubComponent {
    fun inject(listBoardFragment: ListBoardFragment?)
}