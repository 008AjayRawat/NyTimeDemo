package com.app.nytimesdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.nytimesdemo.R
import com.app.nytimesdemo.data.models.NewsResult
import com.app.nytimesdemo.ui.holder.NewsItemViewHolder
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.util.*

class NewsRecyclerViewAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NewsItemViewHolder?>(), View.OnClickListener {

    private var mItemList: MutableList<NewsResult> = ArrayList()


    fun setItemList(items: MutableList<NewsResult>) {
        mItemList = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_list_content, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.itemView.tag = mItemList[position]
        holder.itemView.setOnClickListener(this)
        val item: NewsResult = mItemList[position]
        holder.mTextViewNewsTitle?.text = item.title
        holder.mTextViewByLine?.text = item.byline
        holder.mTextViewNewsDate?.text = item.publishedDate
        if (item.media!!.isNotEmpty()) {
            val media = item.media[0]
            if (media?.mediaMetadata?.isNotEmpty() == true) {
                val url = media.mediaMetadata[0]?.url
                Picasso.get().load(url)
                    .placeholder(R.drawable.progress_animation)
                    .transform(CropCircleTransformation())
                    .into(holder.mImageViewNews)
            }
        }
    }

    override fun onClick(view: View) {
        val item = view.tag as NewsResult
        itemClickListener.onItemClickListener(item)
    }

    interface OnItemClickListener {
        fun onItemClickListener(item: NewsResult)
    }

}