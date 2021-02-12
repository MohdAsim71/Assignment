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
import java.nio.charset.Charset
import java.util.HashMap

class NewsListInterecter(mPresenter: NewsListPresenter) : NewsListMVPContract.ModelOperations {

    private var mPresenter: NewsListMVPContract.ModelPresenterOperationCallBack? = null
   private val mView: WeakReference<NewsListMVPContract.ViewOperationsCallBack>? = null


    init {
        this.mPresenter = mPresenter

    }

    override fun initVolleyCallFetchNews(context: Context?) {


/*
        if (Utility.chkStatus(context as Activity)) {
*/

            val mUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c9ff93ef987e46789ee4f791498335d5"
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
                        mPresenter!!.onAPIResponseError(context)


                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["User-Agent"] = "Morzilla/5.0"
                        return headers
                    }


                    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
                        return try {
                            var cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
                            if (cacheEntry == null) {
                                cacheEntry = Cache.Entry()
                            }
                            val cacheHitButRefreshed = (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
                            val cacheExpired = (2*60*60 * 1000).toLong() // in 2 hours this cache entry expires completely
                            val now = System.currentTimeMillis()
                            val softExpire = now + cacheHitButRefreshed
                            val ttl = now + cacheExpired
                            cacheEntry.data = response.data
                            cacheEntry.softTtl = softExpire
                            cacheEntry.ttl = ttl


                            var headerValue: String?
                            headerValue = response.headers["Date"]
                            if (headerValue != null) {
                                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
                            }
                            headerValue = response.headers["Last-Modified"]
                            if (headerValue != null) {
                                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
                            }
                            cacheEntry.responseHeaders = response.headers
                            // final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                            // val json = String(response?.data ?: ByteArray(0), Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
                            val jsonString = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers)))

                            Response.success(JSONObject(jsonString), cacheEntry)
                        } catch (e: UnsupportedEncodingException) {
                            Response.error(ParseError(e))
                        } catch (e: JSONException) {
                            Response.error(ParseError(e))
                        }
                    }

                    override fun deliverResponse(response: JSONObject) {
                        super.deliverResponse(response)
                    }

                    override fun deliverError(error: VolleyError) {
                        super.deliverError(error)
                    }

                    override fun parseNetworkError(volleyError: VolleyError): VolleyError {
                        return super.parseNetworkError(volleyError)
                    }
                }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)

/*
        } else {
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show()

        }*/
    }
}