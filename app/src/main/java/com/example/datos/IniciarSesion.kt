package com.example.datos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.datos.databinding.IniciarSesionBinding
import com.google.firebase.auth.FirebaseAuth

class IniciarSesion : AppCompatActivity() {
    lateinit var binding: IniciarSesionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Iniciar Sesión")
        binding.btnsuscribir.setOnClickListener {
            var correo: String = binding.txtcorreo.text.toString()
            var clave: String = binding.txtclave.text.toString()
            if (correo.isEmpty() == false && clave.isEmpty() == false) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener {
                        val mensaje = AlertDialog.Builder(this)
                        mensaje.setTitle("Advertencia")
                        if (it.isSuccessful) {
                            val i=Intent(this,FrmMenu::class.java )
                            i.putExtra("correo",correo)
                            startActivity(i)
                        } else {
                            mensaje.setMessage("Error al crear cuenta del usuario")
                            mensaje.setPositiveButton("Aceptar", null)
                            val alertar = mensaje.create()
                            alertar.show()
                        }
                    }
            }
        }

        binding.btniniciar.setOnClickListener {
            var correo:String =binding.txtcorreo.text.toString()
            var clave:String =binding.txtclave.text.toString()
            if (correo.isEmpty()==false  && clave.isEmpty()==false) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(correo,clave).addOnCompleteListener {
                    val mensaje=AlertDialog.Builder(this)
                    mensaje.setTitle("Advertencia")
                    if(it.isSuccessful){
                        val i2=Intent(this,FrmContacto::class.java )
                        i2.putExtra("correo",correo)
                        startActivity(i2)
                    }
                    else{
                        mensaje.setMessage("Error al iniciar cuenta del usuario")
                        mensaje.setPositiveButton("Aceptar",null)
                        val alertar = mensaje.create()
                        alertar.show()
                    }
                }
            }
        }

    }
}