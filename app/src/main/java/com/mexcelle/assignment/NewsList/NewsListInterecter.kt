package com.mexcelle.assignment.NewsList

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mexcelle.assignment.NewsList.Pojo.NewsListResponse
import com.mexcelle.assignment.Util.Utility
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.ref.WeakReference
import java.util.HashMap

class NewsListInterecter(mPresenter: NewsListPresenter) : NewsListMVPContract.ModelOperations {

    private var mPresenter: NewsListMVPContract.ModelPresenterOperationCallBack? = null
    private val mView: WeakReference<NewsListMVPContract.ViewOperationsCallBack>? = null


    init {
        this.mPresenter = mPresenter

    }

    override fun initVolleyCallFetchNews(context: Context?) {


        if (Utility.chkStatus(context as Activity)) {

            val mUrl =
                "https://newsapi.org/v2/top-headlines?country=in&apiKey=c9ff93ef987e46789ee4f791498335d5"
            val request: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, mUrl, null, Response.Listener { response ->

                    val gson = Gson()
                    val newsResponse = response.toString()
                    Log.e("response", newsResponse.toString())
                    Utility.newsListResponse =
                        gson.fromJson<NewsListResponse>(
                            newsResponse,
                            NewsListResponse::class.java
                        )
                    if (Utility.newsListResponse!!.status.equals("ok")) {
                        mPresenter!!.onAPIResponseSuccess(context)

                    } else {
                        mPresenter!!.onAPIResponseFailure(context, "No data available")
                    }
                },
                    Response.ErrorListener { error ->
                        Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show()
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["User-Agent"] = "Morzilla/5.0"
                        return headers
                    }

                }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)


        } else {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show()

        }
    }
}