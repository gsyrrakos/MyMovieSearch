package com.giorgosyrr.mymoviesearch.data.remote


import com.giorgosyrr.mymoviesearch.Constants
import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    //very radical solution for some reason on the header throws 401
    @GET(Constants.API_ENDPOINT+"api_key=2810b46c0fe82e2e7eb43466581d495f")
    fun getMovies(@Query("query") queryCode:String, @Query("page") pageCode:Int) : Observable<DataFromApi>


}