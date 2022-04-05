package dev.mwayne.pennydrop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.mwayne.pennydrop.R
import dev.mwayne.pennydrop.databinding.FragmentPickPlayersBinding
import dev.mwayne.pennydrop.viewmodels.GameViewModel
import dev.mwayne.pennydrop.viewmodels.PickPlayersViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PickPlayersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickPlayersFragment : Fragment() {

    private val pickPlayersViewModel
            by activityViewModels<PickPlayersViewModel>()
    private val gameViewModel
        by activityViewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPickPlayersBinding
            .inflate(inflater, container, false)
            .apply {
                this.vm = pickPlayersViewModel

                this.buttonPlayGame.setOnClickListener {
                    gameViewModel.startGame(
                        pickPlayersViewModel.players.value
                            ?.filter { newPlayer ->
                                newPlayer.isIncluded.get()
                            }?.map { newPlayer ->
                                newPlayer.toPlayer()
                            } ?: emptyList()
                    )
                    findNavController().navigate(R.id.gameFragment)
                }
            }

        return binding.root
    }

}