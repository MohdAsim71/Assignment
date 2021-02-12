package com.mexcelle.assignment.NewsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.mexcelle.assignment.NewsList.Adapter.NewsListAdapter
import com.mexcelle.assignment.NewsList.Pojo.ArticlesItem
import com.mexcelle.assignment.R
import com.mexcelle.assignment.Util.Utility
import java.util.*

class NewsListActivity : AppCompatActivity() , NewsListMVPContract.ViewOperationsCallBack{

    private var mRecyclerView: RecyclerView? = null
    private var statusImageView: ImageView? = null
    private var statusTextView: TextView? = null
    private var newsListAdapter: NewsListAdapter? = null
    var articlesList: ArrayList<ArticlesItem>? = null
    private var mRequestQueue: RequestQueue? = null
    private var mWaveSwipeRefreshLayout: SwipeRefreshLayout?=null
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    private var mPresenter: NewsListPresenter? = null
    var statusTimer: Timer? = Timer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        startMVP()
        Init()
        mPresenter?.fetchNews(this);
        mWaveSwipeRefreshLayout!!.setColorSchemeResources(R.color.green);
        mWaveSwipeRefreshLayout!!.setOnRefreshListener {

            mPresenter?.fetchNews(this);
            Toast.makeText(this@NewsListActivity, "Updated", Toast.LENGTH_SHORT).show()


        }
        statusTimer?.schedule(object : TimerTask() {

            @SuppressLint("NewApi")
            override fun run() {
                Log.e("timer", "timer is running")

                if (Utility.chkStatus(this@NewsListActivity)) {
                    runOnUiThread {
                        // Stuff that updates the UI
                        statusTextView!!.text = "Online"
                        statusImageView!!.setImageDrawable(resources.getDrawable(R.drawable.online, null))


                    }
                } else {

                    runOnUiThread {
                        // Stuff that updates the UI
                        statusTextView!!.text = "Offline"
                        statusImageView!!.setImageDrawable(resources.getDrawable(R.drawable.away, null))


                    }

                }

            }
        }, 0, 10000)



    }

    private fun Init() {
        statusImageView = findViewById<View>(R.id.online_status_iv) as ImageView
        statusTextView = findViewById<View>(R.id.online_status_tv) as TextView
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this))
        articlesList = ArrayList<ArticlesItem>()
        mRequestQueue = Volley.newRequestQueue(this)
        shimmerFrameLayout = findViewById<ShimmerFrameLayout>(R.id.shimmer4)
        mWaveSwipeRefreshLayout = findViewById(R.id.main_swipe) as SwipeRefreshLayout

    }


    override fun onResume() {
        super.onResume()
        shimmerFrameLayout!!.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout!!.stopShimmerAnimation()
    }


    override fun updateScreenWithNews() {

        articlesList?.clear()
        articlesList = Utility.newsListResponse?.articles as ArrayList<ArticlesItem>?
        newsListAdapter = NewsListAdapter(this, articlesList)
        mRecyclerView!!.adapter = newsListAdapter
        shimmerFrameLayout!!.stopShimmerAnimation()
        shimmerFrameLayout!!.visibility = View.GONE
        mWaveSwipeRefreshLayout!!.setRefreshing(false);

    }
    private fun startMVP() {

        mPresenter = NewsListPresenter(this)

    }
}