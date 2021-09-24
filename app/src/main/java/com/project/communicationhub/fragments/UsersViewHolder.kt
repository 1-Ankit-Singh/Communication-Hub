package com.project.communicationhub.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.project.communicationhub.R
import com.project.communicationhub.models.User
import com.squareup.picasso.Picasso

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User, onClick: (name: String, photo: String, id: String) -> Unit) =
        with(itemView) {
            val count = findViewById<TextView>(R.id.countTv)
            val time = findViewById<TextView>(R.id.timeTv)
            val title = findViewById<TextView>(R.id.titleTv)
            val subTitle = findViewById<TextView>(R.id.subTitleTv)
            val userImg = findViewById<ImageView>(R.id.userImgView)

            count.isVisible = false
            time.isVisible = false

            title.text = user.name
            subTitle.text = user.status
            Picasso.get()
                .load(user.thumbImage)
                .placeholder(R.drawable.defaultavatar)
                .error(R.drawable.defaultavatar)
                .into(userImg)
            setOnClickListener {
                onClick.invoke(user.name, user.thumbImage, user.uid)
            }
        }
}

class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

