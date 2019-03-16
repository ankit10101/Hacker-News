package com.example.hackernews.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.ClickHandler
import com.example.hackernews.R
import com.example.hackernews.models.News
import kotlinx.android.synthetic.main.news_row.view.*

class NewsAdapter(private val newsList: ArrayList<News>, private val handler: ClickHandler) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): NewsHolder {
        context = viewGroup.context
        return NewsHolder(LayoutInflater.from(context).inflate(R.layout.news_row, viewGroup, false))
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(newsHolder: NewsHolder, positon: Int) {
        val currentNews = newsList[positon]
        with(newsHolder.itemView) {
            tvTitle.text = currentNews.title
            tvTime.text = currentNews.time.toString()
            tvUser.text = "Submitted by:- ${currentNews.by}"
            tvUrl.text = currentNews.url
        }
        newsHolder.itemView.setOnClickListener {
            handler.onClick(currentNews)
        }
    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}