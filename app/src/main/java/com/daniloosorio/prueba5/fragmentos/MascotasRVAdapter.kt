package com.daniloosorio.prueba5.fragmentos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.prueba5.R
import com.daniloosorio.prueba5.remote.MascotaRemote
import kotlinx.android.synthetic.main.item_mascota.view.*

class MascotasRVAdapter (
    var mascotasList: ArrayList<MascotaRemote>
):RecyclerView.Adapter<MascotasRVAdapter.MascotasViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_mascota,parent,false)
        return MascotasViewHolder(itemView)
    }

    override fun getItemCount(): Int = mascotasList.size

    override fun onBindViewHolder(holder: MascotasViewHolder, position: Int) {
        val mascota = mascotasList[position]
        holder.binMascotas(mascota)
    }

    class MascotasViewHolder(
        itemView:View
    ):RecyclerView.ViewHolder(itemView){
        fun binMascotas(mascota:MascotaRemote){
            itemView.tv_nombre.text=mascota.nombre
            itemView.tv_tipo.text=mascota.tipo
            itemView.tv_edad.text=mascota.edad + " Años"
            itemView.tv_peso.text=mascota.peso+ " gr"
            itemView.tv_intervalos.text=mascota.intervalos+" horas"
            var dias =""
            if (mascota.l){ dias += "L " }
            if (mascota.m){ dias += "M " }
            if (mascota.w){ dias += "W " }
            if (mascota.j){ dias += "J " }
            if (mascota.v){ dias += "V " }
            if (mascota.s){ dias += "S " }
            if (mascota.d){ dias += "D " }
            itemView.tv_dias.text= dias

        }
    }
}