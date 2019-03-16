package com.example.hackernews

import com.example.hackernews.models.News

interface ClickHandler {
    fun onClick(news : News)
}