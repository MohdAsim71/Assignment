package com.mexcelle.assignment.NewsList.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hashmi.MLTClasses.Animation.MyBounceInterpolator
import com.mexcelle.assignment.NewsDetails.DetailActivity
import com.mexcelle.assignment.NewsList.Pojo.ArticlesItem
import com.mexcelle.assignment.R
import com.squareup.picasso.Picasso
import java.util.*


class NewsListAdapter(
        private val context: Context?,
        mExampleList: ArrayList<ArticlesItem>?
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val mExampleList: ArrayList<ArticlesItem>
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val v: View =
            LayoutInflater.from(context).inflate(R.layout.card_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
    ) {
        val mCurrentItem: ArticlesItem = mExampleList[position]
        val mImageurl: String = mCurrentItem.urlToImage.toString()
        val mTittle: String? = mCurrentItem.title
        val mDescription: String? = mCurrentItem.description
        val mauthor: String = mCurrentItem.author.toString()
        val mContent: String = mCurrentItem.content.toString()
        val mpublishedAt: String = mCurrentItem.publishedAt.toString()
        val mUrl: String = mCurrentItem.url.toString()

        holder.mTextView.text = mTittle
        holder.mDate.text="Time "+mpublishedAt

        var animation = AnimationUtils.loadAnimation(context, R.anim.button_animation)
        var interpolator = MyBounceInterpolator(0.1, 20.0)
        animation.setInterpolator(interpolator)
        holder.itemView.animation = animation


        if (mauthor.equals("null"))
        {
            holder.mContent.text = ""

        }
        else
        {
            holder.mContent.text = "By "+mauthor

        }


        if (mImageurl.isEmpty()) {
            holder.mImage.setImageResource(R.drawable.exp);
        } else{
            Picasso.with(context).load(mImageurl).placeholder(R.drawable.exp).fit().centerCrop()
                .into(holder.mImage)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
             intent.putExtra("mImageurl", mImageurl)
             intent.putExtra("mContent", mContent)
             intent.putExtra("mDescription", mDescription)
             intent.putExtra("mTittle", mTittle)
             intent.putExtra("mpublishedAt", mpublishedAt)
             intent.putExtra("mauthor", mauthor)
             intent.putExtra("mUrl", mUrl)

            context!!.startActivity(intent)
         }
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mImage: ImageView
        val mTextView: TextView
        val mContent: TextView
        val mDate: TextView

        init {
            mImage = itemView.findViewById(R.id.imageView)
            mTextView = itemView.findViewById(R.id.textView)
            mContent = itemView.findViewById(R.id.content)
            mDate= itemView.findViewById(R.id.date)
        }
    }

    init {
        this.mExampleList = mExampleList!!
    }
}
