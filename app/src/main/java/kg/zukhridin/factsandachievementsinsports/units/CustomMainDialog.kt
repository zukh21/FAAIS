package kg.zukhridin.factsandachievementsinsports.units

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kg.zukhridin.factsandachievementsinsports.MainActivity
import javax.inject.Inject
import javax.inject.Singleton
class CustomMainDialog {
    companion object{
        fun dialog(context: Context, contentView: Int): Dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(contentView)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.width = WindowManager.LayoutParams.FILL_PARENT
        }
    }

}