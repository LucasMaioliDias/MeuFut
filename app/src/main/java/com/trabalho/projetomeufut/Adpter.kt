package com.trabalho.projetomeufut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adpter(

    private val myList:List<String>,
    private val myDescriptions: List<String>,
    val nameSelect:(String) -> Unit

):RecyclerView.Adapter<Adpter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_quadras,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = myList[position]
        val description = myDescriptions[position]
        holder.textname.text = name
        holder.descricao.text = description

        holder.textname.setOnClickListener{nameSelect(name)}

    }
class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val textname: TextView = itemView.findViewById(R.id.pfv)
    val descricao: TextView = itemView.findViewById(R.id.descricao)


}


}
