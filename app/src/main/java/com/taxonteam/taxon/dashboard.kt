package com.taxonteam.taxon

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.dialogue_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class dashboard : AppCompatActivity() {

    private lateinit var mDialog:Dialog

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

        setNavigationDrawer()

        mDialog = Dialog(this)
        setDialogueBox()

        savebtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                name = nameet.text.toString()
                uid = uidet.text.toString()
                email = emailuseret.text.toString()
                phone = phoneet.text.toString()
                mRef.child("users").child(userid.toString()).child("name").setValue(name)
                mRef.child("users").child(userid.toString()).child("uid").setValue(uid)
                mRef.child("users").child(userid.toString()).child("email").setValue(email)
                mRef.child("users").child(userid.toString()).child("phone").setValue(phone)
                Log.i("keysearch",name+uid+email+phone);
            }
            datasaveview.visibility=View.INVISIBLE
        }

        val searchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    //Key exists
                    Log.i("keysearch","reading");
                    fetchUserData()
                } else {
                    //Key does not exist
                    Log.i("keysearch","writing");
                    datasaveview.visibility=View.VISIBLE
                    //Log.i("keysearch","writing");
                    //fetchUserData()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        }
        mRef.child("users").orderByKey().equalTo(userid).addValueEventListener(searchListener)
    }

    private fun setDialogueBox() {

        mDialog.setContentView(R.layout.dialogue_layout)
        val window = mDialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCanceledOnTouchOutside(false) // prevent dialog box from getting dismissed on outside touch
        mDialog.setCancelable(false)  //prevent dialog box from getting dismissed on back key pressed
        mDialog.show()

        mDialog.close_btn.setOnClickListener {
            finish()
        }

        mDialog.save_btn.setOnClickListener {
            name = mDialog.name.text.toString()
            uid = mDialog.uid.text.toString()
            email = mDialog.email.toString()
            phone = mDialog.phone.toString()

            when {
                name.isEmpty() -> {
                    mDialog.name.error = "Enter your name."
                }
                uid.isEmpty() -> {
                    mDialog.uid.error = "Enter UID."
                }
                email.isEmpty() || (!validEmail(email) )-> {
                    mDialog.email.error = "Enter valid Email"
                }
                phone.isEmpty() || (!validCellPhone(phone)) -> {
                    mDialog.phone.error = "Enter valid 10-digit Number"
                }
                else -> {
                    mDialog.dismiss()
                }
            }
        }
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

    //Check for valid Email
    private fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //Check for Valid Phone Number
    fun validCellPhone(number: String?): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }

}
