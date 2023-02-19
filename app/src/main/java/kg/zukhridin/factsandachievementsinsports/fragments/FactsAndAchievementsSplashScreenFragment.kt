package kg.zukhridin.factsandachievementsinsports.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.zukhridin.factsandachievementsinsports.R
import kg.zukhridin.factsandachievementsinsports.databinding.FragmentFactsAndAchievementsBinding

@AndroidEntryPoint
class FactsAndAchievementsSplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentFactsAndAchievementsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFactsAndAchievementsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_factsAndAchievementsSplashScreenFragment_to_factsAndAchievementsFragment)
        }, 1000)
    }
}