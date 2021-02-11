package com.mexcelle.assignment.NewsDetails

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.mexcelle.assignment.R

class DetailActivity : AppCompatActivity() {

    var webView:WebView?=null
    var searchTv:TextView?=null
    var urlTv:TextView?=null
    var shareIV:ImageView?=null
    var closeIV:ImageView?=null
    var toolbar:Toolbar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


       // toolbar = findViewById(R.id.toolbar2)

        closeIV = findViewById(R.id.close_image)
        shareIV = findViewById(R.id.share_img)
        searchTv = findViewById(R.id.search_ed)
        urlTv = findViewById(R.id.url_text)
        webView = findViewById(R.id.webview)
        webView!!.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY)
        webView!!.getSettings().setBuiltInZoomControls(true)
        webView!!.getSettings().setUseWideViewPort(true)
        webView!!.getSettings().setLoadWithOverviewMode(true)
        webView!!.settings.javaScriptEnabled=true
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()


        webView!!.setWebViewClient(object : android.webkit.WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(
                    this@DetailActivity,
                    "Error:$description",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        webView!!.loadUrl(intent.getStringExtra("mUrl").toString())


        val mUrl = intent.getStringExtra("mUrl")
        val title = intent.getStringExtra("title")

        searchTv!!.text=title
        urlTv!!.text=mUrl
        val url: String = intent.getStringExtra("mUrl").toString()
        Log.e("data", url)

        //Utility.displayProgressDialogue(this);
        //  getTermsAndConditions();
        shareIV!!.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)

            // Add data to the intent, the receiving app will decide
            // what to do with it.

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            share.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("mUrl"))

            startActivity(Intent.createChooser(share, "Share link!"))
        }
        closeIV!!.setOnClickListener {
            onBackPressed()
        }


    }


}