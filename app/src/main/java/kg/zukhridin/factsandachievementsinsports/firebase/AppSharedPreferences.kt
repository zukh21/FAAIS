package kg.zukhridin.factsandachievementsinsports.firebase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    private val prefs = context.getSharedPreferences("url_website", Context.MODE_PRIVATE)
    private val _urlStateFlow = MutableStateFlow<String?>(null)
    val urlStateFlow: StateFlow<String?> = _urlStateFlow.asStateFlow()
    private val urlKey = "url"

    init {
        val url = prefs.getString(urlKey, null)
        if (!url.isNullOrEmpty()) {
            _urlStateFlow.value = url
        }
    }

    @Synchronized
    fun setAuth(url: String) {
        _urlStateFlow.value = url
        with(prefs.edit()) {
            putString(urlKey, url)
            apply()
        }
    }
}