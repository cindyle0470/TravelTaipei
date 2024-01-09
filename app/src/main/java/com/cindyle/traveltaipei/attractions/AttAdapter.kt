package com.cindyle.traveltaipei.attractions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cindyle.traveltaipei.R
import com.cindyle.traveltaipei.bean.AttBean

class AttAdapter () : RecyclerView.Adapter<AttAdapter.ViewHolder>(){
    private var itemClickListener : AttListener ?= null
    private var list: List<AttBean.AttData>? = null
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val info : TextView = view.findViewById(R.id.info)
        val itemAtt : ConstraintLayout = view.findViewById(R.id.item_att)
        val img : ImageView = view.findViewById(R.id.img)
    }

    interface AttListener {
        fun onClick(pos: Int, data: AttBean.AttData?)
    }

    fun setListener(listener : AttListener){
        itemClickListener = listener
    }

    fun setList(list: List<AttBean.AttData>, context : Context) {
        this.list = list
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_att, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list != null) {
            val data: AttBean.AttData = list!![position]
            holder.title.text = data.name
            holder.info.text = data.introduction.trim()

            if (data.images.size != 0) {
                val imgUrl = data.images[0].src
                Glide.with(context).load(imgUrl).into(holder.img)
            } else {
                Glide.with(context).load(R.drawable.ic_photo).into(holder.img)
            }

            holder.itemAtt.setOnClickListener{
                itemClickListener?.onClick(position, data);
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

}