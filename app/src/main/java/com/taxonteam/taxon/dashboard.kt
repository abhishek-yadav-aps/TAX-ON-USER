package com.taxonteam.taxon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.taxonteam.taxon.ui.dataRecorder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import java.io.File

class dashboard : AppCompatActivity() {
    val mRef=FirebaseDatabase.getInstance().getReference()
    val mAuth=FirebaseAuth.getInstance()
    val userid=mAuth.currentUser?.uid
    var name:String=""
    var uid:String=""
    var email:String=""
    var phone:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setNavigationDrawer();

        //mRef.child("users").child(userid.toString()).child("email").setValue("test@gmail.com")
        val searchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    //Key exists
                    Log.i("keysearch","reading");
                    fetchUserData()

                } else {
                    //Key does not exist
                    Log.i("keysearch","writing");
                    supportFragmentManager.beginTransaction().add(R.id.datafrag, dataRecorder()
                    ).commitNow()
                    fetchUserData()

                }
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        }
        mRef.child("users").orderByKey().equalTo(userid).addValueEventListener(searchListener)


    }

    private fun fetchUserData() {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name= dataSnapshot.child("name").getValue(String::class.java).toString()
                uid= dataSnapshot.child("uid").getValue(String::class.java).toString()
                email= dataSnapshot.child("email").getValue(String::class.java).toString()
                phone= dataSnapshot.child("phone").getValue(String::class.java).toString()
                //Log.i("datafetched",data)
                Log.i("keysearch",name+uid+email+phone);
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        }
        if (userid != null) {
            mRef.child("users").child(userid).addValueEventListener(dataListener)
        }
    }

    private fun setNavigationDrawer() {
          nav_drawer.setNavigationItemSelectedListener {
              when(it.itemId){
                R.id.menu_profile -> {
                                        Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                                        return@setNavigationItemSelectedListener true }
                R.id.menu_setting -> true
                  R.id.menu_out -> true
                  else -> { return@setNavigationItemSelectedListener  false}
              }
          }

        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }



}
