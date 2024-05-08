package com.oelnooc.earthquakesretriever.data.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.LoginViewModel
import com.oelnooc.earthquakesretriever.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        with(binding) {
            logBtn.setOnClickListener {
                viewModel.onLoginButtonClicked()
                viewModel.navigateToEventMap.observe(viewLifecycleOwner, Observer { navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_loginFragment2_to_eventMapFragment)
                        viewModel.doneNavigatingToEventMap()
                    }
                })
            }

            regTv.setOnClickListener {
                viewModel.onRegisterTextClicked()
                viewModel.navigateToRegistry.observe(viewLifecycleOwner, Observer { navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_loginFragment2_to_registryFragment)
                        viewModel.doneNavigatingToRegistry()
                    }
                })
            }
        }

        return binding.root
    }

}