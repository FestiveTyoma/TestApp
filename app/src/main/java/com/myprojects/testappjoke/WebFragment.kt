package com.myprojects.testappjoke

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


class WebFragment : Fragment(), IOnBackPressed {
    private val LINK_API = "https://www.icndb.com/api/"
    private lateinit var webView: WebView
    private var webViewBundle: Bundle? = null
   companion object {
       private var mInstance: WebFragment? = null
       fun getInstance(): WebFragment? {
           if (mInstance == null) {
               mInstance = WebFragment()
           }
           return mInstance
       }
   }
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        webView = view.findViewById(R.id.webView)
        webView.getSettings().javaScriptEnabled = true
        setSettingsWeb()
        if (webViewBundle != null) {
            webView.restoreState(webViewBundle!!)
        } else {
            webView.loadUrl(LINK_API)
        }
        webView.setWebViewClient(MyWebViewClient())
        return view
    }

    private fun setSettingsWeb() {
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onPause() {
        super.onPause()
        webViewBundle = Bundle()
        webView.saveState(webViewBundle!!)
    }

    // back button implementation
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }

    //to open links in the browser
    private class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        // For old device
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

}