package ru.daniilxt.common.extensions

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.RadioGroup
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.NonCancellable.children
import ru.daniilxt.common.utils.DebounceClickListener
import ru.daniilxt.common.utils.DebouncePostHandler

@Suppress("DEPRECATION")
fun View.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            systemUiVisibility = flags
        }
    }
}

@Suppress("DEPRECATION")
fun View.clearLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            var flags = systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            systemUiVisibility = flags
        }
    }
}

fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this).toInt() }
        top?.run { topMargin = dpToPx(this).toInt() }
        right?.run { rightMargin = dpToPx(this).toInt() }
        bottom?.run { bottomMargin = dpToPx(this).toInt() }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Float = context.dpToPx(dp)

/** Not really a delegate, just a small helper for RecyclerView.ViewHolders */
inline fun <T : ViewBinding> ViewGroup.viewBinding(factory: (LayoutInflater, ViewGroup, Boolean) -> T) =
    factory(LayoutInflater.from(context), this, false)