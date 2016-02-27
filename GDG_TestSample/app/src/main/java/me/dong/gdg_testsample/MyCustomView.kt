package me.dong.gdg_testsample

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

/**
 * Created by Dong on 2016-02-28.
 */
class MyCustomView : LinearLayout {
    private lateinit var image: ImageView
    private lateinit var text: TextView

    private fun init() = AnkoContext.createDelegate(this).apply {
        gravity = Gravity.CENTER
        padding = dip(4)

        image = imageView(R.mipmap.ic_launcher) {
            onClick {
                startAnimation()
            }

            padding = dip(8)
            layoutParams = LinearLayout.LayoutParams(dip(48), dip(48))
        }

        text = textView("Anko Custom View") {
            textSize = 15f
        }

        startAnimation()
    }

    private fun startAnimation() {
        if (image.animation != null) return

        image.startAnimation(ScaleAnimation(1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                .apply {
                    repeatCount = 1
                    repeatMode = Animation.REVERSE
                    duration = 1500
                })
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun ViewManager.myCustomView() = myCustomView {}

inline fun ViewManager.myCustomView(init: MyCustomView.() -> Unit) = ankoView({ MyCustomView(it) }, init)
