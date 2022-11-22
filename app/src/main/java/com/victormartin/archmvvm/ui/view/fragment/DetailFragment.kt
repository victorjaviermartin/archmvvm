package com.victormartin.archmvvm.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.victormartin.archmvvm.R
import com.victormartin.archmvvm.databinding.FragmentDetailBinding
import com.victormartin.archmvvm.ui.viewmodel.DataViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    val dataViewModel: DataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val player = dataViewModel.latestSelectedPlayer.value

        if(player != null){
            binding.playerName.text = "${resources.getString(R.string.player_name)} ${player.firstName} ${player.lastName}"
            binding.playerPosition.text = "${resources.getString(R.string.player_position)} ${player.position}"
            binding.playerHeightfeet.text = "${resources.getString(R.string.player_heightfeet)} ${player.heightFeet}"
            binding.playerWeightpounds.text = "${resources.getString(R.string.player_weightpounds)} ${player.weightPounds}"

            binding.teamName.text = "${resources.getString(R.string.team_name)} ${player.team?.fullName}"
            binding.teamAbbreviation.text = "${resources.getString(R.string.team_abbreviation)} ${player.team?.abbreviation}"
            binding.teamCity.text = "${resources.getString(R.string.team_city)} ${player.team?.city}"

            when(player.team?.conference){
                "East" ->
                    binding.teamAvatarImg.load(dataViewModel.eastIcon){
                        crossfade(true)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }
                "West" ->
                    binding.teamAvatarImg.load(dataViewModel.westIcon){
                        crossfade(true)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }
                else ->
                    binding.teamAvatarImg.load(
                        ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher_foreground, null)
                    ){
                        crossfade(true)
                        placeholder(R.mipmap.ic_launcher_foreground)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }

            }

        }

        dataViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if(!isLoading){
                binding.progressStatsBar.visibility = View.GONE
                binding.stats.visibility = View.VISIBLE
            }else{
                binding.progressStatsBar.visibility = View.VISIBLE
                binding.stats.visibility = View.GONE
            }
        })

        dataViewModel.playerStats.observe(viewLifecycleOwner, Observer { stats ->
            if(stats != null && stats.gamesPlayed != -1){
                binding.stats.text =
                    "Games played: ${stats.gamesPlayed}\n" +
                            "Minutes: ${stats.min}\n" +
                            "Average points: ${stats.fga}"
            }else{
                binding.stats.text = "NO ACTIVE"
            }
        })

        binding.progressStatsBar.visibility = View.VISIBLE
        binding.stats.visibility = View.GONE

        binding.searchYearStats.setOnClickListener {
            dataViewModel.getStatsByPlayerId(Integer.valueOf(binding.statsYear.text.toString()), dataViewModel.latestSelectedPlayer.value?.let { listOf(it.id) }!!)
        }

        return binding.root
    }

}