package com.taxonteam.taxon

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
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
import kotlinx.android.synthetic.main.content_dashboard_two.*
import kotlinx.android.synthetic.main.dialogue_layout.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class dashboard : AppCompatActivity() {

    private lateinit var mDialog:Dialog

    val mRef=FirebaseDatabase.getInstance().getReference()
    val mAuth=FirebaseAuth.getInstance()
    val userid=mAuth.currentUser?.uid
    var name:String=""
    var uid:String=""
    var email:String=""
    var phone:String=""

    var PhoneNumberOfUser = "" //This entity has not been used to show in the UI. So this variable is there to keep track of it.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setNavigationDrawer()

        lottieAnimationView2.setMinAndMaxFrame(0,196)
        lottieAnimationView2.playAnimation()

        val searchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    //Key exists
                    Log.i("keysearch","reading");
                    fetchUserData()
                } else {
                    //Key does not exist
                    Log.i("keysearch","writing");
                    mDialog = Dialog(this@dashboard)
                    setDialogueBox()
                    //Log.i("keysearch","writing");
                    //fetchUserData()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        }
        mRef.child("users").orderByKey().equalTo(userid).addValueEventListener(searchListener)

          //Edit PROFILE ICON

        nav_drawer.getHeaderView(0).user_edit.setOnClickListener {
            mDialog = Dialog(this@dashboard)
            setNavHeaderDialogueBox()
        }


    }

    private fun setNavHeaderDialogueBox(){
        mDialog.setContentView(R.layout.dialogue_layout)
        val window = mDialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCanceledOnTouchOutside(false) // prevent dialog box from getting dismissed on outside touch
        mDialog.setCancelable(false)  //prevent dialog box from getting dismissed on back key pressed
        mDialog.show()

        //Setting original Content before hand
        mDialog.Dname.setText(user_name.text)
        mDialog.Duid.setText(user_uid.text)
        mDialog.Demail.setText(user_email.text)
        mDialog.Dphone.setText(PhoneNumberOfUser)

        mDialog.close_btn.setOnClickListener {
            mDialog.dismiss()
        }

        mDialog.save_btn.setOnClickListener {
            name = mDialog.Dname.text.toString()
            uid = mDialog.Duid.text.toString()
            email = mDialog.Demail.text.toString()
            phone = mDialog.Dphone.text.toString()

            when {
                name.isEmpty() -> {
                    mDialog.Dname.error = "Enter your name."
                }
                uid.isEmpty() -> {
                    mDialog.Duid.error = "Enter UID."
                }
                !validEmail(email) -> {
                    mDialog.Demail.error = "Enter valid Email"
                }
                !validCellPhone(phone) -> {
                    mDialog.Dphone.error = "Enter valid 10-digit Number"
                }
                else -> { //Save Button
                    mRef.child("users").child(userid.toString()).child("name").setValue(name)
                    mRef.child("users").child(userid.toString()).child("uid").setValue(uid)
                    mRef.child("users").child(userid.toString()).child("email").setValue(email)
                    mRef.child("users").child(userid.toString()).child("phone").setValue(phone)

                    user_name.text = name
                    user_email.text = email
                    user_uid.text = uid
                    PhoneNumberOfUser = phone
                    mDialog.dismiss()
                }
            }
        }
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
            name = mDialog.Dname.text.toString()
            uid = mDialog.Duid.text.toString()
            email = mDialog.Demail.text.toString()
            phone = mDialog.Dphone.text.toString()

            when {
                name.isEmpty() -> {
                    mDialog.Dname.error = "Enter your name."
                }
                uid.isEmpty() -> {
                    mDialog.Duid.error = "Enter UID."
                }
                !validEmail(email)-> {
                    mDialog.Demail.error = "Enter valid Email"
                }
                !validCellPhone(phone)  -> {
                    mDialog.Dphone.error = "Enter valid 10-digit Number"
                }
                else -> { //Save Button
                    mRef.child("users").child(userid.toString()).child("name").setValue(name)
                    mRef.child("users").child(userid.toString()).child("uid").setValue(uid)
                    mRef.child("users").child(userid.toString()).child("email").setValue(email)
                    mRef.child("users").child(userid.toString()).child("phone").setValue(phone)

                    user_name.text = name
                    user_email.text = email
                    user_uid.text = uid
                    PhoneNumberOfUser = phone
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
                Log.i("keysearch",name+uid+email+phone)

                user_name.text = name
                user_email.text = email
                user_uid.text = uid
                PhoneNumberOfUser = phone
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
                R.id.menu_profile -> { Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                                        return@setNavigationItemSelectedListener true }
                R.id.menu_setting -> true
                  R.id.menu_out -> {
                      signOut()
                      true
                  }
                  else -> { return@setNavigationItemSelectedListener  false}
              }
          }

        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    //Check for valid Email
    private fun validEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //Check for Valid Phone Number
    fun validCellPhone(number: String?): Boolean {
        return !TextUtils.isEmpty(number) && android.util.Patterns.PHONE.matcher(number).matches()
    }
    fun signOut(){
        mAuth.signOut()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
