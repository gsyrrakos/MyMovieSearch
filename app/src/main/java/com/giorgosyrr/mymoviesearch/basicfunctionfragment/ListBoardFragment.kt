package com.giorgosyrr.mymoviesearch.basicfunctionfragment


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giorgosyrr.mymoviesearch.BaseApplication
import com.giorgosyrr.mymoviesearch.MainActivity
import com.giorgosyrr.mymoviesearch.R
import com.giorgosyrr.mymoviesearch.basicfunctionfragment.adapter.CustomAdapter

import com.giorgosyrr.mymoviesearch.basicfunctionfragment.dialog.PersonalInfoDialog
import com.giorgosyrr.mymoviesearch.data.model.DataFromApi
import com.giorgosyrr.mymoviesearch.data.model.Results
import com.giorgosyrr.mymoviesearch.databinding.FragmentBasicFunctionBinding
import com.giorgosyrr.mymoviesearch.di.FragmentModule
import javax.inject.Inject


class ListBoardFragment : Fragment(), ListBoardContract.View {
    private var listCached: List<Results?>? = null

    @set:Inject
    var presenter: ListBoardContract.Presenter? = null
    private var _binding: FragmentBasicFunctionBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var listBoard: DataFromApi? = null
    private var searchView: SearchView? = null
    private var movieQuery: String? = null
    private val isNetworkAvailable: Boolean
        get() {
            val cm: ConnectivityManager =
                requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicFunctionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (this.requireActivity().application as BaseApplication).appComponent
            ?.converterActivitySubcomponent(FragmentModule(this))
            ?.inject(this)
        val act = activity as MainActivity?

        recyclerView = binding.myRecyclerView
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        searchView = binding.idSV


        // listener for our search view.
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listCached = null
                movieQuery = query
                recyclerView?.adapter = adapter
                binding.myRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                presenter?.getData(isNetworkAvailable, query ?: "alien", 1)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


        act?.findViewById<Toolbar>(R.id.tb_main)?.findViewById<ImageView>(R.id.refresh)?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                PersonalInfoDialog().show(act.supportFragmentManager, "info-dialog")
            }
        }

        act?.findViewById<Toolbar>(R.id.tb_main)?.findViewById<ImageView>(R.id.back)?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }



        if (listCached == null) {
            binding.progressBar.visibility = View.VISIBLE
            presenter?.getData(isNetworkAvailable)
        } else {
            adapter = CustomAdapter(listCached!! as ArrayList<Results?>)
            recyclerView?.adapter = adapter
            binding.myRecyclerView.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter?.onDestroy()
    }

    override fun loadFirstData(dataFromApi: DataFromApi) {
        binding.progressBar.visibility = View.GONE
        listCached = dataFromApi.results
        this.listBoard = dataFromApi
        adapter = CustomAdapter(dataFromApi.results)
        recyclerView?.adapter = adapter
        initScrollListener(dataFromApi)
        binding.myRecyclerView.visibility = View.VISIBLE
    }

//for paging custom solution
    private fun initScrollListener(items: DataFromApi) {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == items.results.size - 1) {
                        if (listBoard != null) {
                            if ((listBoard?.page ?: 0) <= (listBoard?.totalPages ?: 0)) {
                                presenter?.getDataRemoteNextPage(
                                    listBoard?.page ?: 0,
                                    true,
                                    movieQuery ?: "alien",
                                    items.results
                                )

                                isLoading = true
                            }

                        }

                    }
                }
            }
        })
    }

    override fun displayDownloadResults(dataFromApi: DataFromApi, items: ArrayList<Results?>) {
        binding.progressBar.visibility = View.GONE
        listBoard = dataFromApi
        items.add(null)
        adapter?.notifyItemInserted(items.size - 1)
        val handler = Handler()
        handler.postDelayed({
            items.removeAt(items.size - 1)
            val scrollPosition: Int = items.size
            adapter?.notifyItemRemoved(scrollPosition)
            items.addAll(dataFromApi.results)
            adapter?.notifyDataSetChanged()
            isLoading = false
        }, 2000)
    }


    override fun displayNetworkError(text: String?) {
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }

}