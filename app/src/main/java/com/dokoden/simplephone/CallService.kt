package com.dokoden.simplephone

import android.content.Intent
import android.telecom.Call
import android.telecom.InCallService
import androidx.lifecycle.ViewModelProvider

class CallService : InCallService() {
    private val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }

    override fun onCallAdded(call: Call) {
        viewModel.call.postValue(call)
        Intent(this, MainActivity::class.java).let {
            it.action = when (call.state) {
                Call.STATE_NEW -> Constants.Actions.New.name
                Call.STATE_DIALING -> Constants.Actions.Dialing.name
                Call.STATE_RINGING -> Constants.Actions.Ringing.name
                Call.STATE_HOLDING -> Constants.Actions.Holding.name
                Call.STATE_ACTIVE -> Constants.Actions.Active.name
                Call.STATE_DISCONNECTED -> Constants.Actions.Disconnected.name
                Call.STATE_SELECT_PHONE_ACCOUNT -> Constants.Actions.SelectPhoneAccount.name
                Call.STATE_CONNECTING -> Constants.Actions.Connecting.name
                Call.STATE_DISCONNECTING -> Constants.Actions.Disconnecting.name
                Call.STATE_PULLING_CALL -> Constants.Actions.PullingCall.name
                Call.STATE_AUDIO_PROCESSING -> Constants.Actions.AudioProcessing.name
                Call.STATE_SIMULATED_RINGING -> Constants.Actions.SimulatedRinging.name
                else -> Constants.Actions.Kill.name
            }
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
        }
    }

    override fun onCallRemoved(call: Call) {
        viewModel.call.postValue(call)
        Intent(this, MainActivity::class.java).let {
            it.action = Constants.Actions.DeclineIncomingCall.name
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
        }
    }
}