package com.mexcelle.assignment.NewsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import java.util.ArrayList

class NewsListActivity : AppCompatActivity() , NewsListMVPContract.ViewOperationsCallBack{

    private var mRecyclerView: RecyclerView? = null
    private var newsListAdapter: NewsListAdapter? = null
    var articlesList: ArrayList<ArticlesItem>? = null
    private var mRequestQueue: RequestQueue? = null
    private var mWaveSwipeRefreshLayout: SwipeRefreshLayout?=null
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    private var mPresenter: NewsListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        startMVP()
        Init()
        mPresenter?.fetchNews(this);
        mWaveSwipeRefreshLayout!!.setColorSchemeResources(R.color.green);
        mWaveSwipeRefreshLayout!!.setOnRefreshListener {

            mPresenter?.fetchNews(this);

        }

    }

    private fun Init() {
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