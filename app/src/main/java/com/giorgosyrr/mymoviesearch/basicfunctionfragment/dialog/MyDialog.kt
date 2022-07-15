package com.giorgosyrr.mymoviesearch.basicfunctionfragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.giorgosyrr.mymoviesearch.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PersonalInfoDialog :
    BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.DialogRoundedCornersNoNavBarDim

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog_info_sheet, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.dialog_close_button)?.setOnClickListener {
            dismiss()
        }
        view.findViewById<Button>(R.id.got_it_button)?.setOnClickListener {
            dismiss()
        }

    }
}