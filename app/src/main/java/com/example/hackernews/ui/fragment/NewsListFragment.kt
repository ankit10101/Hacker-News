package com.example.hackernews.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.ClickHandler
import com.example.hackernews.models.News
import com.example.hackernews.ui.adapter.NewsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_news_list.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


@SuppressLint("ValidFragment")
class NewsListFragment : Fragment() {
    private val gson = Gson()
    private val newsList = ArrayList<News>()
    private var TAG = javaClass.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.hackernews.R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val client = OkHttpClient()
        val request = Request.Builder().url("https://hacker-news.firebaseio.com/v0/topstories.json").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                client.newCall(request).enqueue(this)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val result = response.body()?.string()
                val listType = object : TypeToken<List<Long>>() {}.type
                val topStories = gson.fromJson<ArrayList<Long>>(result, listType)
                result?.let {
                    for (topStory in topStories) {
                        val requestForNews =
                            Request.Builder().url("https://hacker-news.firebaseio.com/v0/item/$topStory.json")
                                .build()
                        val newCall = client.newCall(requestForNews)
                        newCall.enqueue(object : Callback {
                            override fun onFailure(call: okhttp3.Call, e: IOException) {
                                e.printStackTrace()
                                client.newCall(request).enqueue(this)
                            }

                            override fun onResponse(call: okhttp3.Call, response: Response) {
                                val responseBody = response.body()
                                val newResult = responseBody?.string()
                                val news = gson.fromJson(newResult, News::class.java)
                                newsList.add(news)
                            }
                        })
                    }
                }
            }
        })
        val newsAdapter = NewsAdapter(newsList, activity as ClickHandler)
        rvNews.adapter = newsAdapter
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            rvNews.context,
            LinearLayoutManager.VERTICAL
        )
        rvNews.addItemDecoration(dividerItemDecoration)
        newsAdapter.notifyDataSetChanged()
    }
}
