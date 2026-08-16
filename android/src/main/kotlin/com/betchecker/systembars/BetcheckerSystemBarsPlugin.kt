package com.betchecker.systembars

import android.os.Build
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

class BetcheckerSystemBarsPlugin : FlutterPlugin, ActivityAware {

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.activity.window.isNavigationBarContrastEnforced = false
            binding.activity.window.navigationBarColor =
    android.graphics.Color.parseColor("#EF8439")
        }
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.activity.window.isNavigationBarContrastEnforced = false
            binding.activity.window.navigationBarColor =
    android.graphics.Color.parseColor("#EF8439")
        }
    }

    override fun onDetachedFromActivity() {
    }
}
