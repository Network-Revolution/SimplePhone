package com.dokoden.simplephone

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.dokoden.simplephone.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mainViewModel by viewModels<MainViewModel>()
        val mainHostFragment =
            requireParentFragment().parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        FragmentThirdBinding.inflate(inflater, container, false).also {
            it.viewModel = mainViewModel
            it.lifecycleOwner = viewLifecycleOwner
            it.answer.setOnClickListener {
                mainViewModel.answer()
            }
            it.hangup.setOnClickListener {
                mainViewModel.hangup()
            }
            mainViewModel.call.observe(viewLifecycleOwner) { call ->
                it.callInfo.text = when (call.state) {
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
                    else -> {
                        Log.w("CALL.STATE", "Unknown state $this")
                        "UNKNOWN"
                    }
                } + "\n${call.details.handle.schemeSpecificPart}"
            }

            return it.root
        }
    }
}