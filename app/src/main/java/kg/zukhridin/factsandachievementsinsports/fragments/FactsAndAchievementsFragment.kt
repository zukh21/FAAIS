package kg.zukhridin.factsandachievementsinsports.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.zukhridin.factsandachievementsinsports.R
import kg.zukhridin.factsandachievementsinsports.databinding.FragmentFactsAndAchievementsBinding

class FactsAndAchievementsFragment : Fragment() {
    private lateinit var binding: FragmentFactsAndAchievementsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFactsAndAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackClick()
        binding.footballFactsAndAchievements.setOnClickListener {
            findNavController().navigate(R.id.action_factsAndAchievementsFragment_to_FragmentFootballFactsAndAchievementsBinding)
        }
    }

    private fun onBackClick() {

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    println("onBackClick d")
                    requireActivity().finish()
                }

            })

    }
}