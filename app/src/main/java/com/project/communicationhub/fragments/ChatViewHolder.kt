package com.project.communicationhub.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.project.communicationhub.R
import com.project.communicationhub.models.Inbox
import com.project.communicationhub.utils.formatAsListItem
import com.squareup.picasso.Picasso

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Inbox, onClick: (name: String, photo: String, id: String) -> Unit) =
        with(itemView) {
            val count = findViewById<TextView>(R.id.countTv)
            val time = findViewById<TextView>(R.id.timeTv)
            val title = findViewById<TextView>(R.id.titleTv)
            val subTitle = findViewById<TextView>(R.id.subTitleTv)
            val userImg = findViewById<ImageView>(R.id.userImgView)

            count.isVisible = item.count > 0
            count.text = item.count.toString()
            time.text = item.time.formatAsListItem(context)

            title.text = item.name
            subTitle.text = item.msg
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.defaultavatar)
                .error(R.drawable.defaultavatar)
                .into(userImg)
            setOnClickListener {
                onClick.invoke(item.name, item.image, item.from)
            }
        }
}