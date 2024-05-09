package com.oelnooc.earthquakesretriever.data.ui.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.LoginViewModel
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.factory.LoginViewModelFactory
import com.oelnooc.earthquakesretriever.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val factory = LoginViewModelFactory(sharedPreferences)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        with(binding) {
            logBtn.setOnClickListener {
                val username = nameEt.text.toString().trim()
                val password = passEt.text.toString().trim()

                viewModel.onLoginButtonClicked(username, password)
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

        viewModel.showInvalidCredentialsSnackbar.observe(viewLifecycleOwner, Observer { showSnackbar ->
            if (showSnackbar) {
                Snackbar.make(binding.root, viewModel.errorMessage.value ?: "Error", Snackbar.LENGTH_SHORT).show()
                viewModel.doneShowingInvalidCredentialsSnackbar()
            }
        })

        return binding.root
    }

}