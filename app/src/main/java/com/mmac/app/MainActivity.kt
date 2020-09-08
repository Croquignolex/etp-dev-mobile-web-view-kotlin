package com.mmac.app

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.webView
import kotlinx.android.synthetic.main.activity_main.swipeToRefresh

class MainActivity : AppCompatActivity() {
    private var backPressedTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init web view
        webView.webViewClient = WebViewClient()
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        // Set web view settings
        val webSettings = webView.settings
        webSettings.useWideViewPort = true
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.safeBrowsingEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // Load url
        webView.loadUrl("https://www.etp.dmsemergence.com/")

        refreshApp()
    }

    // Handle back button pressed
    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            // Navigate to previous page if browser history exist
            webView.goBack()
        } else {
            if((backPressedTime + 2000) > System.currentTimeMillis()) {
                // Can exit if there is 2 seconds between the back button double tap
                super.onBackPressed()
            } else {
                // Neither toast call to action message
                Toast.makeText(
                    applicationContext,
                    "Appuiyez à nouveau pour quitter",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // Update user back pressed time
            backPressedTime = System.currentTimeMillis()
        }
    }

    // Handle swipe down to refresh app
    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            // Reload web view
            webView.reload()
            swipeToRefresh.isRefreshing = false
        }
    }
}