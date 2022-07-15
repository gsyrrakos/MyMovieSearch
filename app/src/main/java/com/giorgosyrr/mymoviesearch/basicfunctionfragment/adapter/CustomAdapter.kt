package com.giorgosyrr.mymoviesearch.basicfunctionfragment.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giorgosyrr.mymoviesearch.MainActivity
import com.giorgosyrr.mymoviesearch.R
import com.giorgosyrr.mymoviesearch.basicfunctionfragment.detailsfragment.DetailsFragment
import com.giorgosyrr.mymoviesearch.data.model.Results


class CustomAdapter(var data: ArrayList<Results?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            val model = data[position]
            if (model != null) {
                populateItemRows(viewHolder, model)
            }
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val linearLayout: LinearLayout
        val mTextView: TextView
        val mTextViewRating: TextView
        val iconPreview: ImageView


        init {
            linearLayout = itemView.findViewById(R.id.linear_layout)
            mTextView = itemView.findViewById(R.id.textViewName)
            iconPreview = itemView.findViewById(R.id.image_icon)
            mTextViewRating = itemView.findViewById(R.id.textViewRating)
        }

    }

    private inner class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }


    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }


    private fun populateItemRows(holder: ItemViewHolder, model: Results) {
        holder.mTextView.text = model.originalTitle
        Glide.with(holder.iconPreview).load("https://image.tmdb.org/t/p/w500" + model.backdropPath)
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.iconPreview)

        holder.mTextViewRating.text = model.voteAverage.toString()

        onClickDetails(holder, model)
    }


    private fun onClickDetails(holder: ItemViewHolder, model: Results) {

        holder.linearLayout.setOnClickListener {
            val myActivity = holder.itemView.context as MainActivity
            val myFragment = DetailsFragment.newInstance(
                model.originalTitle ?: "",
                model.posterPath ?: "",
                model.overview ?: "",
                model.voteAverage.toString() ?: ""
            )

            myActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.myContainer, myFragment).addToBackStack("my").commit()
        }
    }
}