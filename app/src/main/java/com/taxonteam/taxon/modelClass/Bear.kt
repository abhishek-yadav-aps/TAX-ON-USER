package com.taxonteam.taxon.modelClass

import com.airbnb.lottie.LottieAnimationView

class Bear(var bearView: LottieAnimationView) {
    companion object{
        const val TRACKING_START =138.0f
        const val TRACKING_END = 242.0f
        const val TOTAL_FRAMES = 300.0f

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
        bearView.setAnimation("teddy.json")
        bearView.loop(true)
        bearView.setMinAndMaxFrame(1, 25)
        bearView.playAnimation()
    }

    fun idle(){
        currentState = Idle
        bearView.setMinAndMaxFrame(1, 25)
        bearView.loop(true)
        bearView.playAnimation()
    }

    fun fail(){
        currentState = Fail
        bearView.setMinAndMaxFrame(105, 130)
        bearView.loop(false)
        bearView.playAnimation()
    }

    fun handsup(){
        if(checkCurrentState() != Handsup){
            bearView.setMinAndMaxFrame(244, 270)
            bearView.loop(false)
            bearView.playAnimation()
        }
        currentState = Handsup
    }

    fun handsdown(){
        if(checkCurrentState() != HandsDown){
            bearView.setMinAndMaxFrame(271, 299)
            bearView.loop(false)
            bearView.playAnimation()
        }
        currentState = HandsDown
    }

    fun looking_aroung(fl:Float){
        if(checkCurrentState() != LookingAround){
            bearView.setMinAndMaxFrame(138, 242)
        }
        bearView.loop(false)
        currentState = LookingAround
        val progress = position(fl, TRACKING_START/ TOTAL_FRAMES, TRACKING_END/ TOTAL_FRAMES)
        bearView.progress = progress
    }

    fun success(){
        bearView.setMinAndMaxFrame(30, 79)
        bearView.loop(false)
        bearView.playAnimation()
    }

    fun position(progress:Float, minframe:Float, maxframe:Float ): Float{
        return (minframe + (maxframe - minframe) * progress)
    }

}