package com.giorgosyrr.mymoviesearch.data.repository




import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface DataRepository {

    fun getMovies(queryCode:String,pageCode:Int): Observable<DataFromApi>


}