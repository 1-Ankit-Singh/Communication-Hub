package com.project.communicationhub.viewholders

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.project.communicationhub.R
import com.project.communicationhub.interaction.OthersProfileActivity
import com.project.communicationhub.models.Inbox
import com.project.communicationhub.utils.formatAsListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: Inbox,
        onClick: (name: String, photo: String, id: String, context: Context) -> Unit
    ) =
        with(itemView) {
            countTv.isVisible = item.count > 0
            countTv.text = item.count.toString()
            timeTv.text = item.time.formatAsListItem(context)
            titleTv.text = item.name
            subTitleTv.text = item.msg
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.defaultavatar)
                .error(R.drawable.defaultavatar)
                .into(userImgView)
            userImgView.setOnClickListener {
                //onClick.invoke(item.name, item.image, item.from)
                context.startActivity(
                    OthersProfileActivity.createOthersProfileActivity(
                        context, item.from
                    )
                )
            }
            setOnClickListener {
                onClick.invoke(item.name, item.image, item.from, context)
            }
        }
}