package com.giorgosyrr.mymoviesearch.data.repository



import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import com.giorgosyrr.mymoviesearch.data.remote.ApiManager
import com.giorgosyrr.mymoviesearch.di.custom_annotation.PerFragment
import io.reactivex.Observable

import javax.inject.Inject


@PerFragment
class DataRepositoryImpl @Inject constructor(apiManager: ApiManager) :
    DataRepository {
    private val apiManager: ApiManager = apiManager

    override fun getMovies(queryCode: String, pageCode: Int): Observable<DataFromApi> {
        return apiManager.getMovies(queryCode,pageCode)
    }
}