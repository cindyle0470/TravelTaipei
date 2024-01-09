package com.cindyle.traveltaipei.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cindyle.traveltaipei.R
import com.cindyle.traveltaipei.bean.NewsBean

class NewsAdapter () : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    private var itemClickListener : NewsListener ?= null
    private var list: List<NewsBean.NewsData>? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val info : TextView = view.findViewById(R.id.info)
        val itemNews : ConstraintLayout = view.findViewById(R.id.item_news)
    }

    interface NewsListener {
        fun onClick(pos: Int, data: NewsBean.NewsData?)
    }

    fun setListener(listener : NewsListener){
        itemClickListener = listener
    }

    fun setList(list: List<NewsBean.NewsData>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list != null) {
            val data: NewsBean.NewsData = list!![position]
            holder.title.text = data.title
            holder.info.text = data.description.trim()
            holder.itemNews.setOnClickListener{
                itemClickListener?.onClick(position, data);
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

}