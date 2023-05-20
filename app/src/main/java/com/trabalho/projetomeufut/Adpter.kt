package com.trabalho.projetomeufut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adpter(
    private val myList: List<String>,
    private val myDescriptions: List<String>,
    private val myImages: List<Int>,
    val nameSelect: (String) -> Unit
) : RecyclerView.Adapter<Adpter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_quadras, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = myList[position]
        val description = myDescriptions[position]
        val image = myImages[position]
        holder.bindData(name, description, image)

        holder.textname.setOnClickListener { nameSelect(name) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textname: TextView = itemView.findViewById(R.id.pfv)
        val descricao: TextView = itemView.findViewById(R.id.descricao)
        val imageView: ImageView = itemView.findViewById(R.id.imglist)

        fun bindData(name: String, description: String, image: Int) {
            textname.text = name
            descricao.text = description
            imageView.setImageResource(image)
        }
    }
}






