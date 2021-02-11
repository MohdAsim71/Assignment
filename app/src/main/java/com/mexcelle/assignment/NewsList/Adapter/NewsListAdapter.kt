package com.mexcelle.assignment.NewsList.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mexcelle.assignment.NewsDetails.DetailActivity
import com.mexcelle.assignment.NewsList.Pojo.ArticlesItem
import com.mexcelle.assignment.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

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
        val mUrl: String = mCurrentItem.url.toString()
        val mContent: String = mCurrentItem.content.toString()
        holder.mTextView.text = mTittle

        if (mContent==null)
        {
            holder.mContent.text = ""

        }
        else
        {
            holder.mContent.text = mContent

        }


        if (mImageurl.isEmpty()) {
            holder.mImage.setImageResource(R.drawable.ic_launcher_background);
        } else{
            Picasso.with(context).load(mImageurl).placeholder(R.drawable.ic_launcher_background).fit().centerCrop()
                .into(holder.mImage)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
             intent.putExtra("mUrl", mUrl)
             intent.putExtra("title",mTittle)
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

        init {
            mImage = itemView.findViewById(R.id.imageView)
            mTextView = itemView.findViewById(R.id.textView)
            mContent = itemView.findViewById(R.id.content)
        }
    }

    init {
        this.mExampleList = mExampleList!!
    }
}
