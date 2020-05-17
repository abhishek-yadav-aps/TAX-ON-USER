package com.taxonteam.taxon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.taxonteam.taxon.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard_two.*
import kotlinx.android.synthetic.main.fragment_data_recorder.*
import java.io.File

class dataRecorder : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_recorder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savebtn.setOnClickListener {
            val dataDir= ContextCompat.getDataDir(requireContext())
            val dataFile= File(dataDir,"userdata.txt")
            val name =nameet.text.toString()
            val UID =uidet.text.toString()
            val phone =phoneet.text.toString()
            dataFile.writeText(name+"\n"+UID+"\n"+phone)
            (datafrag.getParent() as ViewGroup).removeView(datafrag)
        }
    }

}
