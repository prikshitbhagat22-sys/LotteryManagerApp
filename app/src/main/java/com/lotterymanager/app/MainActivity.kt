package com.lotterymanager.app

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.webViewClient = WebViewClient()

        // Bridge object available in the web page as window.AndroidApp
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidApp")

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * JS bridge. The web page calls:
     *   window.AndroidApp.sharePDF(base64Data, filename)
     * This saves the PDF to a cache file and opens Android's native
     * share dialog (Just once / Always) so the user can pick
     * WhatsApp or WhatsApp Business directly.
     */
    inner class WebAppInterface(private val activity: AppCompatActivity) {

        @JavascriptInterface
        fun sharePDF(base64Data: String, filename: String) {
            activity.runOnUiThread {
                try {
                    val bytes = Base64.decode(base64Data, Base64.DEFAULT)

                    val shareDir = File(activity.cacheDir, "pdf_share")
                    if (!shareDir.exists()) shareDir.mkdirs()

                    val safeName = if (filename.isNullOrBlank()) "lottery_report.pdf" else filename
                    val file = File(shareDir, safeName)
                    FileOutputStream(file).use { it.write(bytes) }

                    val uri = FileProvider.getUriForFile(
                        activity,
                        "${activity.packageName}.fileprovider",
                        file
                    )

                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "application/pdf"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        putExtra(Intent.EXTRA_SUBJECT, "Lottery Report")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }

                    // No createChooser() on purpose: Android shows its native
                    // "Open with WhatsApp / WhatsApp Business — Just once / Always"
                    // resolver dialog when more than one app can handle a PDF send.
                    activity.startActivity(sendIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
