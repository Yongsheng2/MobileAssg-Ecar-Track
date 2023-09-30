package com.tarc.edu.etrack.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarc.edu.etrack.R

class NotificationAdapter(private val notificationsList: List<NotificationData>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationItem = notificationsList[position]
        holder.bind(notificationItem)
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(notificationItem: NotificationData) {
            val messageTextView = itemView.findViewById<TextView>(R.id.textViewNotification)
            messageTextView.text = notificationItem.message
        }
    }
}