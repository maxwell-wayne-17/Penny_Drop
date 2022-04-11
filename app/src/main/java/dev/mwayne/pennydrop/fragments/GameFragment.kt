package dev.mwayne.pennydrop.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dev.mwayne.pennydrop.R
import dev.mwayne.pennydrop.databinding.FragmentGameBinding
import dev.mwayne.pennydrop.viewmodels.GameViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    private val gameViewModel by activityViewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            vm = gameViewModel

            textCurrentTurnInfo.movementMethod = ScrollingMovementMethod()

            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

}