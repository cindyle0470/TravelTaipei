package com.cindyle.traveltaipei

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AttWebViewActivity : AppCompatActivity() {
    private val TAG = "LOG_TAG_" + AttWebViewActivity::class.java.simpleName
    private var pgBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_att_web_view)
        pgBar = findViewById(R.id.pgBar)

        showProgress()
        initToolbar()
        val url = intent.getStringExtra("att_url")
        openWebView(url!!)
    }

    private fun initToolbar() {
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        val toolbarTitle : TextView = findViewById(R.id.toolbar_title)
        val title =  intent.getStringExtra("title");
        toolbarTitle.text = title
        toolbar.setNavigationOnClickListener { view: View? ->
            finish()
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(AppCompatActivity.OVERRIDE_TRANSITION_OPEN, R.anim.fin_anim, R.anim.fout_anim)
            } else{
                overridePendingTransition(R.anim.fin_anim, R.anim.fout_anim)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openWebView(url: String) {
        val webView = findViewById<View>(R.id.webView) as WebView
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.w(TAG, "onPageStarted()")
                hideProgress()
            }
        }
    }

    fun showProgress() {
        pgBar!!.visibility = View.VISIBLE
    }

    fun hideProgress() {
        if (pgBar != null) {
            pgBar!!.visibility = View.GONE
        }
    }
}