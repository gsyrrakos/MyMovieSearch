package com.giorgosyrr.mymoviesearch.basicfunctionfragment


import android.util.Log
import com.giorgosyrr.mymoviesearch.data.model.Results
import com.giorgosyrr.mymoviesearch.data.repository.DataRepository
import com.giorgosyrr.mymoviesearch.di.custom_annotation.PerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@PerFragment
class ListBoardPresenter @Inject constructor(
    repository: DataRepository,
    private val view: ListBoardContract.View
) : ListBoardContract.Presenter {

    private var repository: DataRepository = repository
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getData(hasNetwork: Boolean, query:String, page:Int) {
        if (hasNetwork) {
            getDataRemote(query,page)
        }
    }



    private fun getDataRemote(query:String="alien",page:Int=1) {

        compositeDisposable.add(repository.getMovies(query,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listBoard ->
                Log.d("edw", listBoard.toString())
                //  repository.insertData(listBoard)

               view.loadFirstData(listBoard) }
            ) { throwable -> view.displayNetworkError(throwable.toString())
            Log.d("edw",throwable.toString())})

    }


    override fun getDataRemoteNextPage(
        currentPage: Int,
        hasNetwork: Boolean,
        query:String,
        items: ArrayList<Results?>
    ) {
        if (hasNetwork) {

            compositeDisposable.add(repository.getMovies(query,currentPage+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dataFromApi -> view.displayDownloadResults(dataFromApi,items) }
                ) { throwable -> view.displayNetworkError(throwable.toString()) })

        }

    }


    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}