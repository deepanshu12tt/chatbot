package com.example.chatbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.data.message
import com.example.chatbot.utils.constants.RECIEVE_ID
import com.example.chatbot.utils.constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class messagingAdapter(var messagelist: MutableList<message> = mutableListOf<message>() ):RecyclerView.Adapter<messagingAdapter.MessageVieHolder>() {
   // var messagelist= mutableListOf<message>()
   inner class MessageVieHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
    init {
        itemView.setOnClickListener{
            messagelist.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVieHolder {
        return MessageVieHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false))

    }

    override fun onBindViewHolder(holder: MessageVieHolder, position: Int) {
        val currentMessage=messagelist[position]
        when(currentMessage.id)
        {
            SEND_ID->
            {
                holder.itemView.tv_message.apply {
                    text=currentMessage.message
                    visibility=View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility=View.GONE
            }
            RECIEVE_ID->
            {
                holder.itemView.tv_bot_message.apply {
                    text=currentMessage.message
                    visibility=View.VISIBLE
                }
                holder.itemView.tv_message.visibility=View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
      return messagelist.size
    }
    fun insertmessage(message: message)
    {
        this.messagelist.add(message)
        notifyItemInserted(messagelist.size)
        notifyDataSetChanged()
    }
}

