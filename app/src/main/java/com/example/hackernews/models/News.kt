package com.example.hackernews.models

import java.io.Serializable

class News (
    val by: String,
    val title: String,
    val url: String,
    val id: Long,
    val time: Int,
    val kids: ArrayList<Long>
) : Serializable
