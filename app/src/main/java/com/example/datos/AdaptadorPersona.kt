package com.example.datos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.datos.databinding.ElementosBinding
import com.squareup.picasso.Picasso
import java.util.*

class AdaptadorPersona(private val dataSet: MutableList<contacto>) :
RecyclerView.Adapter<AdaptadorPersona.ViewHolder>(){
    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        var binding:ElementosBinding=ElementosBinding.bind(view)
        var contexto:Context= view.context
        fun enlazarItem(p:contacto){
            var vinculo=p.Imagen.toString()
            binding.foto.setImageBitmap(null)
            if (vinculo.toString().length > 0) {
                Picasso.get().load(vinculo).into(binding.foto)
            }

                binding.lbldatos.text =
                    "APELLIDOS Y NOMBRES:\n" + p.Apellido + ", " + p.Nombre + "\nDIRECCIÓN:" + p.Direccion + "\nTELEFONO:" + p.Telefono
                binding.root.setOnClickListener {
                    /*val i= Intent(contexto, FrmNuevoContacto::class.java)
                i.putExtra("proceso","mostrar")
                i.putExtra("id",p.idPersona.toString())
                contexto.startActivity(i)*/
                }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorPersona.ViewHolder {
        val vista=LayoutInflater.from(parent.context).inflate(R.layout.elementos,parent,false)
        return ViewHolder(vista)
    }
    override fun onBindViewHolder(holder: AdaptadorPersona.ViewHolder, position: Int) {
            val elemento: contacto = dataSet.get(position)
            holder.enlazarItem(elemento)
    }
    override fun getItemCount()=dataSet.size
}