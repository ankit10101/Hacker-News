package com.example.hackernews.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.models.Comments
import kotlinx.android.synthetic.main.comments_row.view.*

class CommentsAdapter(val commentsList: ArrayList<Comments>): RecyclerView.Adapter<CommentsAdapter.CommentAdapter>() {
    lateinit var context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CommentAdapter {
        context = viewGroup.context
        return CommentAdapter(LayoutInflater.from(context).inflate(R.layout.comments_row,viewGroup,false))
    }

    override fun getItemCount(): Int  = commentsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(commentHolder: CommentAdapter, position: Int) {
        val currentComment = commentsList.get(position)
        with(commentHolder.itemView){
            tvCommentTitle.text = currentComment.text
            tvCommentUser.text = "Submitted by:- ${currentComment.by}"
            tvCommentTime.text = currentComment.time.toString()
        }
    }

    inner class CommentAdapter(itemView: View): RecyclerView.ViewHolder(itemView)

}