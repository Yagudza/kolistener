package com.android.kolistener

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_item_layout.view.*

class RvAdapter(private val siteList: ArrayList<Model>, private val context: Context) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    private val adapterClickEvent = SingleLiveEvent<Model>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter_item_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    fun getAdapterClick() = adapterClickEvent

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = siteList[position].name
        holder.url.text = siteList[position].url
        holder.status.text = siteList[position].status.toString()
        holder.bindListener(siteList[position], adapterClickEvent, context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tvName
        val url: TextView = itemView.tvUrl
        val status: TextView = itemView.tvStatus
        val cardView: CardView = itemView.cvItem

        fun bindListener(
            item: Model,
            adapterClickEvent: SingleLiveEvent<Model>,
            context: Context
        ) {
            if (item.status == 200) {
                cardView.setCardBackgroundColor(context.getColor(R.color.colorOK))
            } else {
                cardView.setCardBackgroundColor(context.getColor(R.color.colorAlarm))
            }
            itemView.setOnClickListener {
                adapterClickEvent.value = item
            }
        }
    }
}