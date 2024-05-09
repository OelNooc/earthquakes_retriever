package com.oelnooc.earthquakesretriever.data.ui.views

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.RegistryViewModel
import com.oelnooc.earthquakesretriever.databinding.FragmentRegistryBinding

class RegistryFragment : Fragment() {

    private lateinit var viewModel: RegistryViewModel
    private lateinit var binding: FragmentRegistryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistryBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        viewModel = RegistryViewModel(sharedPreferences)

        binding.regBtn.setOnClickListener {
            val nombre = binding.nombreEt.text.toString().trim()
            val apellido = binding.apellidoEt.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val contraseña = binding.contraEt.text.toString().trim()

            viewModel.saveUserData(nombre, apellido, email, contraseña)

            findNavController().navigate(R.id.action_registryFragment_to_loginFragment2)
        }

        return view
    }
}