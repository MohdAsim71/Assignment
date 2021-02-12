package com.mexcelle.assignment.NewsDetails

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.mexcelle.assignment.NewsList.Pojo.ArticlesItem
import com.mexcelle.assignment.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    var webView:WebView?=null
    var titleTV:TextView?=null
    var contentTv:TextView?=null
    var description:TextView?=null
    var urlTv:TextView?=null
    var shareIV:ImageView?=null
    var closeIV:ImageView?=null
    var newsImagev:ImageView?=null
    var toolbar:Toolbar?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


       // toolbar = findViewById(R.id.toolbar2)

        titleTV = findViewById(R.id.title_tv)
        contentTv = findViewById(R.id.content_tv)
        description = findViewById(R.id.details_tv)
        closeIV=findViewById(R.id.close_image)
        newsImagev = findViewById(R.id.imageView)
        shareIV = findViewById(R.id.share_img)
        urlTv = findViewById(R.id.url_text)
   /*     webView = findViewById(R.id.webview)
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
                        "No internet Connection",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
        webView!!.loadUrl(intent.getStringExtra("mUrl").toString())*/

        val mImageurl = intent.getStringExtra("mImageurl")
        val mContent = intent.getStringExtra("mContent")
        val mDescription = intent.getStringExtra("mDescription")
        val mpublishedAt = intent.getStringExtra("mpublishedAt")
        val mauthor = intent.getStringExtra("mauthor")
        val mTittle = intent.getStringExtra("mTittle")

            titleTV!!.text=mTittle
        description!!.text=mDescription
        contentTv!!.text=mContent

        if (mImageurl!!.isEmpty()) {
            newsImagev!!.setImageResource(R.drawable.exp);
        } else{
            Picasso.with(this).load(mImageurl).placeholder(R.drawable.exp).fit().centerCrop()
                    .into(newsImagev)
        }
        newsImagev
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