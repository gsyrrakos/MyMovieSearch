package com.giorgosyrr.mymoviesearch.basicfunctionfragment

import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import com.giorgosyrr.mymoviesearch.data.model.Results


interface ListBoardContract {
    interface View {
        fun loadFirstData(dataFromApi: DataFromApi)
        fun displayNetworkError(text: String?)
        fun displayDownloadResults(dataFromApi: DataFromApi, items: ArrayList<Results?>)
    }

    interface Presenter {
        fun getData(hasNetwork: Boolean,query:String="alien",page:Int=1)

        fun getDataRemoteNextPage(
             currentPage: Int,
             hasNetwork: Boolean,
             query:String="alien",
             items: ArrayList<Results?>
        )
        fun onDestroy()
    }
}