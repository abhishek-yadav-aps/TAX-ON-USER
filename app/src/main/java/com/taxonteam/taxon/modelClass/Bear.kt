package com.taxonteam.taxon.modelClass

import com.airbnb.lottie.LottieAnimationView

class Bear(var bearView: LottieAnimationView) {
    companion object{
        const val TRACKING_START =4.0f
        const val TRACKING_END = 76.0f
        const val TOTAL_FRAMES = 80.0f

        const val Idle = 0
        const val Fail = 1
        const val Handsup = 2
        const val HandsDown = 3
        const val Success = 4
        const val LookingAround = 5

        var currentState = 0
    }

    fun checkCurrentState(): Int{
        return currentState
    }

    init {
        currentState = Idle
        bearView.setAnimation("idle.json")
        bearView.loop(true)
        bearView.playAnimation()
    }

    fun idle(){
        currentState = Idle
        bearView.setAnimation("idle.json")
        bearView.loop(true)
        bearView.playAnimation()
    }

    fun fail(){
        currentState = Fail
        bearView.setAnimation("fail.json")
        bearView.loop(false)
        bearView.playAnimation()
    }

    fun handsup(){
        if(checkCurrentState() != Handsup){
            bearView.setAnimation("hands_up.json")
            bearView.playAnimation()
        }
        bearView.loop(false)
        currentState = Handsup
    }

    fun handsdown(){
        currentState = HandsDown
        bearView.setAnimation("hands_down.json")
        bearView.loop(false)
        bearView.playAnimation()
    }

    fun looking_aroung(fl:Float){
        if(checkCurrentState() != LookingAround){
            bearView.setAnimation("looking_around.json")
        }
        bearView.loop(false)
        currentState = LookingAround
        val progress = position(fl, 0f, 1f, TRACKING_START/ TOTAL_FRAMES, TRACKING_END/ TOTAL_FRAMES)
        bearView.progress = progress
    }

    fun success(){
        bearView.setAnimation("success.json")
        bearView.loop(false)
        bearView.playAnimation()
    }

    fun position(progress:Float, start:Float, stop:Float, minframe:Float, maxframe:Float ): Float{
        return (minframe + (maxframe - minframe) * progress)
    }

}