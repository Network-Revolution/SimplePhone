package com.dokoden.simplephone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.dokoden.simplephone.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mainViewModel by viewModels<MainViewModel>()
        val mainHostFragment =
            requireParentFragment().parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        FragmentFirstBinding.inflate(inflater, container, false).also {
            it.viewModel = mainViewModel
            it.lifecycleOwner = viewLifecycleOwner
            it.phoneNumberInput.setOnEditorActionListener { _, _, _ ->
                startActivity(
                    Intent(
                        Intent.ACTION_CALL,
                        "tel:${it.phoneNumberInput.text}".toUri()
                    )
                )
                true
            }
            return it.root
        }
    }
}