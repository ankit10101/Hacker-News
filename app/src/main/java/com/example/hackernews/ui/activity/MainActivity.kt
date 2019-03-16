package com.example.hackernews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hackernews.ClickHandler
import com.example.hackernews.R
import com.example.hackernews.models.News
import com.example.hackernews.ui.fragment.CommentsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickHandler {
    override fun onClick(news: News) {
        if (container == null) {
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("news",news)
            startActivity(intent)
        } else {
            val commentsFragment = CommentsFragment(news)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, commentsFragment)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

