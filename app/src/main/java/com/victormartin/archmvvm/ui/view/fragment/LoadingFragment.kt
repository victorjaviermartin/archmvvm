package com.victormartin.archmvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.victormartin.archmvvm.databinding.FragmentLoadingBinding
import com.victormartin.archmvvm.ui.view.HubActivity
import com.victormartin.archmvvm.ui.viewmodel.DataViewModel

class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding

    val dataViewModel: DataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)

        dataViewModel.eastWestIconsLoaded.observe(viewLifecycleOwner, Observer { iconsLoaded ->
            if(iconsLoaded && dataViewModel.playersLoaded.value == true){
                binding.animationView.cancelAnimation()
                (activity as HubActivity).navigateToListPlayers()
            }
        })

        dataViewModel.playersLoaded.observe(viewLifecycleOwner, Observer { playersLoaded ->
            if(playersLoaded && dataViewModel.eastWestIconsLoaded.value == true){
                binding.animationView.cancelAnimation()
                (activity as HubActivity).navigateToListPlayers()
            }
        })

        return binding.root
    }

}