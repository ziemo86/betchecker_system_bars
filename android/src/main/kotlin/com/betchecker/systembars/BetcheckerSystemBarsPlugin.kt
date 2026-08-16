package com.betchecker.systembars

import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

class BetcheckerSystemBarsPlugin : FlutterPlugin, ActivityAware {

    private var navBarBackground: View? = null
    private var statusBarBackground: View? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        setupSystemBars(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        navBarBackground = null
        statusBarBackground = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        setupSystemBars(binding)
    }

    override fun onDetachedFromActivity() {
        navBarBackground = null
        statusBarBackground = null
    }

    private fun setupSystemBars(binding: ActivityPluginBinding) {
        val activity = binding.activity
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        val decorView = window.decorView as ViewGroup

        val bottomBackground = View(activity).apply {
            setBackgroundColor(Color.parseColor("#EF8439"))
            isClickable = false
            isFocusable = false
        }

        val topBackground = View(activity).apply {
            setBackgroundColor(Color.parseColor("#E5E5E5"))
            isClickable = false
            isFocusable = false
        }

        navBarBackground?.let {
            decorView.removeView(it)
        }

        statusBarBackground?.let {
            decorView.removeView(it)
        }

        navBarBackground = bottomBackground
        statusBarBackground = topBackground

        decorView.addView(bottomBackground)
        decorView.addView(topBackground)

        decorView.setOnApplyWindowInsetsListener { _, insets ->

            val bottomInset =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insets.getInsets(
                        WindowInsets.Type.tappableElement()
                    ).bottom
                } else {
                    @Suppress("DEPRECATION")
                    insets.systemWindowInsetBottom
                }

            val topInset =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insets.getInsets(
                        WindowInsets.Type.statusBars()
                    ).top
                } else {
                    @Suppress("DEPRECATION")
                    insets.systemWindowInsetTop
                }

            bottomBackground.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                bottomInset
            ).apply {
                gravity = Gravity.BOTTOM
            }

            topBackground.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                topInset
            ).apply {
                gravity = Gravity.TOP
            }

            bottomBackground.bringToFront()
            topBackground.bringToFront()

            insets
        }

        decorView.requestApplyInsets()
    }
}
