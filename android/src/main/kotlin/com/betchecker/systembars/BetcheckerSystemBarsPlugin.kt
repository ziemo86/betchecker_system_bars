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

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        setupNavigationBar(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        navBarBackground = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        setupNavigationBar(binding)
    }

    override fun onDetachedFromActivity() {
        navBarBackground = null
    }

    private fun setupNavigationBar(binding: ActivityPluginBinding) {
        val activity = binding.activity
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        val decorView = window.decorView as ViewGroup

        val background = View(activity).apply {
            setBackgroundColor(Color.parseColor("#EF8439"))
            isClickable = false
            isFocusable = false
        }

        navBarBackground?.let {
            decorView.removeView(it)
        }

        navBarBackground = background
        decorView.addView(background)

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

            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                bottomInset
            ).apply {
                gravity = Gravity.BOTTOM
            }

            background.layoutParams = params
            background.bringToFront()

            insets
        }

        decorView.requestApplyInsets()
    }
}
