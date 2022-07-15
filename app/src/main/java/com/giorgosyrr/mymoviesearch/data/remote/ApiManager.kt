package com.giorgosyrr.mymoviesearch.data.remote



import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManager @Inject constructor(private val apiService: ApiService) {

    fun getMovies(queryCode:String,pageCode:Int): Observable<DataFromApi>{
        return apiService.getMovies(queryCode,pageCode)
    }

}