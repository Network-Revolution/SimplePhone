package com.dokoden.simplephone

import android.app.Application
import android.telecom.VideoProfile
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val thisApplication = application
    val call = TempRepository.outgoingCall

    fun answer() {
        call.value?.answer(VideoProfile.STATE_AUDIO_ONLY)
    }

    fun hangup() {
        call.value?.disconnect()
    }
}