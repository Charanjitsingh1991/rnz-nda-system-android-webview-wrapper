package com.rnz.ndasystem

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var errorLayout: LinearLayout
    private lateinit var errorMessage: TextView
    private val FILE_CHOOSER_REQUEST_CODE = 1001
    private val CAMERA_PERMISSION_REQUEST_CODE = 1002
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private val TARGET_URL = "https://rnz-group.com/rnz-registration-page/"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        webView = findViewById(R.id.webView)
        errorLayout = findViewById(R.id.errorLayout)
        errorMessage = findViewById(R.id.errorMessage)
        val btnRetry: Button = findViewById(R.id.btnRetry)

        // Request camera permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }

        setupWebView()
        loadWebPage()

        // Set up the retry button in the error layout
        btnRetry.setOnClickListener {
            hideError()
            loadWebPage()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        // Configure WebView settings
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = false
        webSettings.displayZoomControls = false
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // Set download listener for handling PDF and file downloads
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to download file", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
        
        // Custom WebViewClient for error handling and page load events
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                if (request.isForMainFrame) {
                    showError(getString(R.string.error_no_internet))
                }
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                if (request?.isForMainFrame == true) {
                    when (errorResponse?.statusCode) {
                        404 -> showError(getString(R.string.error_page_not_found))
                        else -> showError(getString(R.string.error_loading_page))
                    }
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                // Allow navigation within the same domain
                return if (url.contains("rnz-group.com")) {
                    false // Let WebView handle it
                } else {
                    // Open external links in browser
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        }
        
        // WebChromeClient for file upload support and other features
        webView.webChromeClient = object : WebChromeClient() {
            // For file upload with camera support
            override fun onShowFileChooser(
                webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivity.filePathCallback = filePathCallback
                
                try {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "*/*"
                    
                    // Create chooser with camera option
                    val chooserIntent = Intent.createChooser(intent, "Select File")
                    
                    // Add camera intent
                    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
                    
                    startActivityForResult(chooserIntent, FILE_CHOOSER_REQUEST_CODE)
                    return true
                } catch (e: Exception) {
                    this@MainActivity.filePathCallback = null
                    e.printStackTrace()
                    return false
                }
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                // Grant permissions for camera, microphone, etc.
                request?.grant(request.resources)
            }
        }
    }

    private fun loadWebPage() {
        webView.loadUrl(TARGET_URL)
    }

    // Handle back button with exit confirmation
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    override fun onBackPressed() {
        // Check if WebView can go back in history
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Show exit confirmation dialog
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.exit_confirmation_title))
        builder.setMessage(getString(R.string.exit_confirmation_message))
        builder.setIcon(R.drawable.logo)
        
        builder.setPositiveButton(getString(R.string.exit_yes)) { _, _ ->
            finish()
        }
        
        builder.setNegativeButton(getString(R.string.exit_no)) { dialog, _ ->
            dialog.dismiss()
        }
        
        builder.setCancelable(true)
        val dialog = builder.create()
        
        // Customize dialog appearance
        dialog.setOnShowListener {
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(this, R.color.rnz_green)
            )
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
                ContextCompat.getColor(this, R.color.rnz_green)
            )
        }
        
        dialog.show()
    }

    // Handle file chooser result for file uploads
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            val results: Array<Uri>? = if (resultCode == Activity.RESULT_OK) {
                if (data?.data != null) {
                    arrayOf(data.data!!)
                } else if (data?.clipData != null) {
                    val clipData = data.clipData!!
                    val uris = Array(clipData.itemCount) { i ->
                        clipData.getItemAt(i).uri
                    }
                    uris
                } else {
                    null
                }
            } else {
                null
            }
            filePathCallback?.onReceiveValue(results)
            filePathCallback = null
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // Handle permission results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Camera permission required for file uploads", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Helper method to show error overlay
    private fun showError(message: String) {
        runOnUiThread {
            errorMessage.text = message
            errorLayout.visibility = LinearLayout.VISIBLE
            webView.visibility = WebView.GONE
        }
    }

    // Helper method to hide error overlay
    private fun hideError() {
        errorLayout.visibility = LinearLayout.GONE
        webView.visibility = WebView.VISIBLE
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
