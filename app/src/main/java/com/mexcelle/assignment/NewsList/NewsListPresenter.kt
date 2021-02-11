package com.mexcelle.assignment.NewsList

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mexcelle.assignment.Util.Utility
import java.lang.ref.WeakReference

class NewsListPresenter(mView: NewsListMVPContract.ViewOperationsCallBack): NewsListMVPContract.ViewPresenterOperations, NewsListMVPContract.ModelPresenterOperationCallBack

{


    private var interactor: NewsListInterecter? = null
    private var mView: WeakReference<NewsListMVPContract.ViewOperationsCallBack>? = null

    init {
        this.mView = WeakReference<NewsListMVPContract.ViewOperationsCallBack>(mView)
        interactor = NewsListInterecter(this)
    }

    override fun fetchNews(activity:Activity) {
        interactor!!.initVolleyCallFetchNews(activity)
    }

    override fun onAPIResponseFailure(context: Context?,error: String?) {

        Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onAPIResponseSuccess(context: Context?) {
        Log.e("success","success")

        if (Utility.newsListResponse?.articles!!.size>0)
        {
            mView?.get()?.updateScreenWithNews()

        }




    }
}