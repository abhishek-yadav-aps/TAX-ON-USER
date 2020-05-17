package com.taxonteam.taxon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.taxonteam.taxon.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard_two.*
import kotlinx.android.synthetic.main.fragment_data_recorder.*
import java.io.File

class dataRecorder : Fragment() {
    val mRef= FirebaseDatabase.getInstance().getReference()
    val mAuth= FirebaseAuth.getInstance()
    val userid=mAuth.currentUser?.uid
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

            val name =nameet.text.toString()
            val uid =uidet.text.toString()
            val email =emailuseret.text.toString()
            val phone =phoneet.text.toString()
            mRef.child("users").child(userid.toString()).child("name").setValue(name)
            mRef.child("users").child(userid.toString()).child("uid").setValue(uid)
            mRef.child("users").child(userid.toString()).child("email").setValue(email)
            mRef.child("users").child(userid.toString()).child("phone").setValue(phone)

        }
    }

}
