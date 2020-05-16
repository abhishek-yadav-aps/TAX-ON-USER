package com.taxonteam.taxon

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val anim = findViewById<LottieAnimationView>( R.id.splash_anim)
        anim.speed = 1f
        anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                Log.e("Animation:","Repeat")
            }
            override fun onAnimationEnd(p0: Animator?) {
                Log.e("Animation:","End")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            override fun onAnimationCancel(p0: Animator?) {
                Log.e("Animation:","cancel")
            }
            override fun onAnimationStart(p0: Animator?) {
                Log.e("Animation:","start")
            }
        })


    }
}
