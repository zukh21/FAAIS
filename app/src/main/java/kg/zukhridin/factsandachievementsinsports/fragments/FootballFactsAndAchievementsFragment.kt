package kg.zukhridin.factsandachievementsinsports.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.*
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kg.zukhridin.factsandachievementsinsports.R
import kg.zukhridin.factsandachievementsinsports.databinding.FragmentFootballFactsAndAchievementsBinding
import kg.zukhridin.factsandachievementsinsports.dto.FAAISDto
import kg.zukhridin.factsandachievementsinsports.utils.CustomMainDialog
import kg.zukhridin.factsandachievementsinsports.viewmodel.FAAISViewModel

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class FootballFactsAndAchievementsFragment : Fragment() {
    private lateinit var binding: FragmentFootballFactsAndAchievementsBinding
    private val viewModel: FAAISViewModel by viewModels()
    private var index = 0
    private lateinit var handler: Handler
    private var correctAnswerCount = 0
    private var wrongAnswerCount = 0
    private var countDown: CountDownTimer? = null
    private var time = 0
    private lateinit var faais: FAAISDto
    private val list = mutableListOf(
        FAAISDto(
            "Which team became world champions in 2022?",
            "France",
            "Nederland",
            "Brazil",
            "" + "Argentina",
            "Argentina"
        ),
        FAAISDto(
            "Which team became world champions in 2018?",
            "France",
            "Nederland",
            "Brazil",
            "" + "Argentina",
            "France"
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFootballFactsAndAchievementsBinding.inflate(inflater, container, false)
        handler = Handler(Looper.getMainLooper())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in list) {
            viewModel.insert(i)
        }

        list.map { faais ->
            binding.question.text = faais.question
            binding.firstOption.text = faais.firstAnswer
            binding.secondOption.text = faais.secondAnswer
            binding.thirdOption.text = faais.thirdAnswer
            binding.fourthOption.text = faais.fourthAnswer
        }
        list.shuffle()
        faais = list[index]
        setAllQA()
        countDownTimer()
        binding.allQuestionsView.text = "1/${list.size}"
        binding.score.text = "${getString(R.string.score)}: $correctAnswerCount"
        optionsClick(binding.firstOption)
        optionsClick(binding.secondOption)
        optionsClick(binding.thirdOption)
        optionsClick(binding.fourthOption)

    }

    private fun optionsClick(button: MaterialButton) {
        button.setOnClickListener {
            countDownTimer()
            if (button.text == list[index].correctAnswer) {
                correctAnswer(button)
            } else {
                wrongAnswer(button)
            }
            disableButton()
            onFinish()
        }
    }

    private fun onFinish() {
        index++
        binding.score.text = "${getString(R.string.score)}: $correctAnswerCount"

        if (index < list.size) {
            faais = list[index]
            handler.postDelayed({
                setAllQA()
                resetBackground()
                enableButton()
                binding.allQuestionsView.text = "${index + 1}/${list.size}"
            }, 500)
        } else {
            disableButton()
            countDown?.cancel()
            handler.postDelayed({
                dialogShow()
            }, 500)
        }
    }


    private fun enableButton() {
        binding.firstOption.isClickable = true
        binding.secondOption.isClickable = true
        binding.thirdOption.isClickable = true
        binding.fourthOption.isClickable = true
    }

    private fun disableButton() {
        binding.firstOption.isClickable = false
        binding.secondOption.isClickable = false
        binding.thirdOption.isClickable = false
        binding.fourthOption.isClickable = false
    }

    private fun resetBackground() {
        binding.firstOption.backgroundTintList =
            ColorStateList.valueOf(Color.argb(255, 255, 125, 178))
        binding.secondOption.backgroundTintList =
            ColorStateList.valueOf(Color.argb(255, 255, 125, 178))
        binding.thirdOption.backgroundTintList =
            ColorStateList.valueOf(Color.argb(255, 255, 125, 178))
        binding.fourthOption.backgroundTintList =
            ColorStateList.valueOf(Color.argb(255, 255, 125, 178))
    }

    private fun setAllQA() {
        binding.question.text = faais.question
        binding.firstOption.text = faais.firstAnswer
        binding.secondOption.text = faais.secondAnswer
        binding.thirdOption.text = faais.thirdAnswer
        binding.fourthOption.text = faais.fourthAnswer
    }

    private fun wrongAnswer(button: MaterialButton) {
        wrongAnswerCount++
        button.backgroundTintList = ColorStateList.valueOf(Color.argb(255, 243, 117, 120))

    }

    private fun correctAnswer(button: MaterialButton) {
        correctAnswerCount++
        button.backgroundTintList = ColorStateList.valueOf(Color.argb(255, 117, 192, 149))
    }


    private fun countDownTimer() {
        countDown?.cancel()
        countDown = object : CountDownTimer(15000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                time = (millisUntilFinished / 1000).toInt()
                if (time <= 5) {
                    binding.stats.setTextColor(Color.RED)
                } else {
                    binding.stats.setTextColor(Color.rgb(123, 123, 123))
                }
                binding.stats.text = time.toString()
            }

            override fun onFinish() {
                binding.stats.text = getString(R.string.time_is_over)
                dialogShow()
            }

        }
        countDown?.start()
    }

    fun dialogShow() {
        disableButton()
        val dialog = CustomMainDialog.dialog(requireContext(), R.layout.dialog)
        val title = dialog.findViewById<TextView>(R.id.title)
        val score = dialog.findViewById<TextView>(R.id.score)
        title.text = getString(R.string.game_over)
        score.text = "${getString(R.string.your_score)}: $correctAnswerCount"
        val ok = dialog.findViewById<MaterialButton>(R.id.ok)
        ok.setOnClickListener {
            dialog.dismiss()
            findNavController().navigateUp()
        }
        dialog.show()
    }

}