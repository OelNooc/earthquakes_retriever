package com.oelnooc.earthquakesretriever.data.ui.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.RegistryViewModel

class RegistryFragment : Fragment() {

    private lateinit var viewModel: RegistryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registry, container, false)
    }
}