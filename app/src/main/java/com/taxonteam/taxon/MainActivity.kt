package com.taxonteam.taxon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.taxonteam.taxon.modelClass.Bear
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var PasswordToggle = false
    private val mAuth=FirebaseAuth.getInstance()
    private lateinit var bear: Bear

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bear = Bear(bearAnim)

        sign_in_forgot.setOnClickListener {
            bear.idle()
            Toast.makeText(this, "Needed to be implemented", Toast.LENGTH_SHORT).show()
        }

        emailet.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                bear.idle()
            }
        }
        passwordet.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                bear.idle()
            }
        }

        emailet.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bear.looking_aroung(getTextWidth(emailet) / emailet.width)
                Log.d("ONTEXTCHANGED",getTextWidth(emailet).toString() +  " -> "+emailet.width.toString() +  (getTextWidth(emailet) / emailet.width))
            }
        })

        passwordet.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text = passwordet.text.toString()
                if (!PasswordToggle && (text.isNotEmpty())) {
                    bear.handsup()
                } else {
                    bear.looking_aroung(getTextWidth(passwordet) / passwordet.width)
                }
            }
        })

        if(!emailet.isFocused && !passwordet.isFocused){
            if(bear.checkCurrentState() == 2){ // Hands Up
                bear.handsdown()
            }
        }


        login_btn.setOnClickListener {
            //check whether email and password fields are empty or not

            //login if fine
            login()
        }




        inputLayout.setEndIconOnClickListener {
            val editText = inputLayout.editText
            val oldSelection = editText?.selectionEnd
            val hidePassword = editText?.transformationMethod !is PasswordTransformationMethod
            passwordet.transformationMethod = PasswordTransformationMethod.getInstance().takeIf { hidePassword }
            if (oldSelection!! >= 0) {
                passwordet.setSelection(oldSelection)
            }
            val text = passwordet.text.toString()
            if(PasswordToggle && text.isNotEmpty()){
            }
            else{
                bear.handsdown()
            }
            PasswordToggle = PasswordToggle!=true
        }
    }

    private fun login () {
        var email = emailet.text.toString()
        var password = passwordet.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener ( this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    bear.success()
                   // startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Successfully Logged in :)", Toast.LENGTH_LONG).show()

                } else {
                    bear.fail()
                    Toast.makeText(this, "Error Logging in :(", Toast.LENGTH_SHORT).show()
                }
            })
        }else {
            bear.fail()
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTextWidth(editText: TextInputEditText): Float {
        return editText.paint.measureText(editText.text.toString())
    }
}
