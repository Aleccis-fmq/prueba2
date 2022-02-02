package com.example.datos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datos.databinding.ActivityFrmContactoBinding
import com.example.datos.databinding.ActivityFrmListarContactoBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.ktx.Firebase
import java.util.*

class FrmListarContacto : AppCompatActivity() {

    var contactos: MutableList<contacto> =mutableListOf()
    //var contactos: MutableList<contacto> = Collections.emptyList<contacto>()
    lateinit var adaptadorPersona: AdaptadorPersona
    lateinit var binding: ActivityFrmListarContactoBinding
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFrmListarContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        EventChangeListener()
        binding.lista.layoutManager=LinearLayoutManager(this)
        binding.lista.setHasFixedSize(true)
    }
    fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("Contactos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   // Log.d(TAG, "${document.id} => ${document.data}")
                    var apellido = document.data["apellido"].toString()
                    var nombre = document.data["nombre"].toString()
                    var direccion = document.data["direccion"].toString()
                    var telefono = document.data["telefono"].toString()
                    var imagen = document.data["imagen"].toString()
                    var reg=contacto(apellido,nombre,direccion,telefono,imagen)
                    contactos.add(reg)
                   // Toast.makeText(this,apellido,3000).show()
                }
                adaptadorPersona= AdaptadorPersona(contactos)
                binding.lista.adapter=adaptadorPersona

            }
            .addOnFailureListener { exception ->
            //    Log.d(TAG, "Error getting documents: ", exception)
            }

    }
}