package com.dokoden.simplephone

import android.telecom.Call
import androidx.lifecycle.MutableLiveData

class TempRepository {
    companion object {
        val outgoingCall = MutableLiveData<Call>()
    }
}