package com.mexcelle.assignment.NewsList

import android.app.Activity
import android.content.Context

class NewsListMVPContract {
    //methods call from Presenter to Activity
    interface ViewOperationsCallBack  {
        fun updateScreenWithNews()
        fun showSnackBaar()
    }

    //methods call from Activity to Presenter
    internal interface ViewPresenterOperations {
        fun fetchNews(activity: Activity)
    }

    //methods call from Interactor to Presenter
    interface ModelPresenterOperationCallBack  {
        fun onAPIResponseFailure(context: Context?, error: String?)
        fun onAPIResponseSuccess(context: Context?)
        fun onAPIResponseError(context: Context?)
    }

    //methods call from Presenter to Interactor
    internal interface ModelOperations  {
        fun initVolleyCallFetchNews(context: Context?)
    }
}