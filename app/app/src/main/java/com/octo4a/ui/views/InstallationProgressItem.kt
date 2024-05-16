package com.octo4a.ui.views

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.octo4a.R
import com.octo4a.repository.ServerStatus
import com.octo4a.utils.animatedAlpha
import com.octo4a.utils.getArchString
import kotlinx.android.synthetic.main.view_installation_item.view.*

class InstallationProgressItem @JvmOverloads
constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyleAttr: Int = 0)
    : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_installation_item, this)
        layoutTransition = LayoutTransition()
    }

    var status: ServerStatus
        get() = ServerStatus.Stopped
        set(value) {
            contentTextView.text = when (value) {
                ServerStatus.DownloadingBootstrap -> resources.getString(R.string.installation_step_downloading, getArchString())
                ServerStatus.ExtractingBootstrap -> resources.getString(R.string.installation_step_extracting, getArchString())
                ServerStatus.BootingUp -> resources.getString(R.string.installation_step_bootup)
                ServerStatus.Running -> resources.getString(R.string.installation_step_done)
                ServerStatus.InstallationError -> resources.getString(R.string.installation_error)
                else -> "Unknown status"
            }
        }

    var statusText: String
        get() = contentTextView.text.toString()
        set(value) {
            contentTextView.text = value
        }
    var isLoading: Boolean
        get() = false
        set(value) {
            spinnerView.isGone = !value
            doneIconView.isGone = value
            if (!value) {
                contentTextView.animatedAlpha = 0.4F
                contentTextView.typeface = Typeface.DEFAULT
            } else {
                contentTextView.animatedAlpha = 1F
                contentTextView.typeface = Typeface.DEFAULT_BOLD
            }
        }

    fun setUpcoming() {
        spinnerView.isGone = true
        doneIconView.isGone = true
        contentTextView.animatedAlpha = 0.4F
        contentTextView.typeface = Typeface.DEFAULT
    }

}