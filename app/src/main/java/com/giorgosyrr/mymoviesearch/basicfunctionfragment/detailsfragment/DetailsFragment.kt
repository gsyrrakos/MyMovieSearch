package com.giorgosyrr.mymoviesearch.basicfunctionfragment.detailsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.giorgosyrr.mymoviesearch.MainActivity
import com.giorgosyrr.mymoviesearch.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"


class DetailsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var mTextView: TextView? = null
    private var mTextViewDescr2: TextView? = null
    private var thumbnailImage: ImageView? = null
    private var mTextViewRating: TextView? = null
    var returnButton: AppCompatButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity?
        mTextView = view.findViewById(R.id.item_title)
        mTextViewDescr2 = view.findViewById(R.id.item_description1)
        mTextViewRating = view.findViewById(R.id.item_rating)
        returnButton = view.findViewById(R.id.return_item_button)
        thumbnailImage = view.findViewById(R.id.item_thumbnail)
        returnButton?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + param2)
            .placeholder(R.drawable.ic_launcher_foreground).into(thumbnailImage!!)
        mTextView?.text = param1
        mTextViewDescr2?.text = param3
        mTextViewRating?.text="Rating : "+param4


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, s: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, s)
                }
            }
    }
}