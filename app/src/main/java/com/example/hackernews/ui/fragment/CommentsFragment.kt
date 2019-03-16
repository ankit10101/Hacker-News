package com.example.hackernews.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.models.Comments
import com.example.hackernews.models.News
import com.example.hackernews.ui.adapter.CommentsAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_news_list.*
import okhttp3.*
import java.io.IOException

@SuppressLint("ValidFragment")
class CommentsFragment : Fragment {
    var news: News? = null

    constructor() : super()
    constructor(receivedNews: News) : super() {
        news = receivedNews
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gson = Gson()
        val commentsList = ArrayList<Comments>()
        super.onViewCreated(view, savedInstanceState)
        val client = OkHttpClient()
        if(news == null){
            news = requireActivity().intent.getSerializableExtra("news") as News
        }
        Log.e("TAG","news is" + news.toString())
        for (kid in news?.kids!!) {
            val requestForComments =
                Request.Builder().url("https://hacker-news.firebaseio.com/v0/item/$kid.json")
                    .build()
            val call = client.newCall(requestForComments)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    client.newCall(requestForComments).enqueue(this)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body()
                    val result = responseBody?.string()
                    Log.e("TAG", result)
                    val comment = gson.fromJson(result, Comments::class.java)
                    commentsList.add(comment)
                }
            })
        }
        val commentsAdapter = CommentsAdapter(commentsList)
        rvComments.adapter = commentsAdapter
        rvComments.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            rvComments.context,
            LinearLayoutManager.VERTICAL
        )
        rvComments.addItemDecoration(dividerItemDecoration)
    }
}
