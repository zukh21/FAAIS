package kg.zukhridin.factsandachievementsinsports.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kg.zukhridin.factsandachievementsinsports.R
import kg.zukhridin.factsandachievementsinsports.databinding.FragmentMainBinding
import kg.zukhridin.factsandachievementsinsports.firebase.AppSharedPreferences
import kg.zukhridin.factsandachievementsinsports.utils.CustomCheckInternet
import kg.zukhridin.factsandachievementsinsports.utils.CustomMainDialog
import javax.inject.Inject

const val GOOGLE = "google"

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var binding: FragmentMainBinding
    private var customSavedInstanceState: Bundle? = null

    @Inject
    lateinit var customCheckInternet: CustomCheckInternet

    @Inject
    lateinit var appSP: AppSharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customSavedInstanceState = savedInstanceState
        checkRemoteUrl()
    }

    private fun checkRemoteUrl() {
        if (appSP.urlStateFlow.value.isNullOrEmpty()) {
            remoteConfigChecks()
        } else {
            checkInternet()
        }
    }

    private fun checkInternet() {
        if (!customCheckInternet.checkInternet()) {
            showDialog()
        }
        openWebView(appSP.urlStateFlow.value!!)
    }

    private fun remoteConfigChecks() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url = remoteConfig.getString("url_website")
                if (url == "" || Build.BRAND == GOOGLE || simCardUnknown()) {
                    findNavController().navigate(R.id.action_mainFragment_to_factsAndAchievementsFragment)
                } else {
                    appSP.setAuth(url)
                    openWebView(url)
                }
            } else {
                Toast.makeText(requireContext(), "Fetch failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun simCardUnknown(): Boolean {
        val telManager =
            requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return when (telManager.simState) {
            TelephonyManager.SIM_STATE_UNKNOWN -> {
                true
            }
            else -> {
                false
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openWebView(url: String) = with(binding) {
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
        }
        if (customSavedInstanceState != null) {
            webView.restoreState(customSavedInstanceState!!)
        } else {
            customBackPressForWebView()
            webView.loadUrl(url)
        }

    }

    private fun customBackPressForWebView() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    binding.webView.goBack()
                }

            })
    }

    private fun showDialog() {
        val dialog = CustomMainDialog.dialog(requireActivity(), R.layout.dialog_for_internet)
        val dialogText = dialog.findViewById<TextView>(R.id.dialogText)
        dialogText.text = getString(R.string.internet_attention_text_for_dialog)
        val ok = dialog.findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            dialog.dismiss()
            checkInternet()
        }
        dialog.show()
    }


}