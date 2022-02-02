package com.example.datos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast

import com.example.datos.databinding.ActivityFrmMenuBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


abstract class FrmMenu : AppCompatActivity() {
    lateinit var binding: ActivityFrmMenuBinding
    var sw=0
    val db=FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFrmMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("Menú Principal")
        sw=0

        val res=intent.extras
        binding.lbldato.text=res?.getString("correo")

        /*binding.btncerrar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }


        binding.btnguardar.setOnClickListener{

           val contacto= db.collection("Contactos")
            var apellido=binding.txtapellido.text.toString()
            var nombre=binding.txtnombre.text.toString()
            var direccion=binding.txtdireccion.text.toString()
            var telefono=binding.txttelefono.text.toString()
            var reg=com.example.datos.contacto(apellido,nombre,direccion,telefono)

            contacto.add(
                reg
            ).addOnSuccessListener {
                Log.d("mensaje", "Added document with ID ${it.id}")
            }
                .addOnFailureListener { exception ->
                    Log.w("mensaje", "Error adding document $exception")
                }

        }
        binding.btnbuscar.setOnClickListener {
            /*//-- Crear Mensaje-----
            val alerta=  AlertDialog.Builder(this);
            alerta.setTitle("Ingresar Dato");
            alerta.setMessage("Nro. de Teléfono:");
            alerta.setView(input);
            alerta.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                binding.txttelefono.setText(input.text.toString())
            })
            alerta.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            alerta.show()*/


            //------------------------

            db.collection("Contactos")
                .whereEqualTo("telefono", binding.txttelefono.text.toString())
                .get()
                .addOnSuccessListener {
                        documents ->
                    binding.txtapellido.setText("")
                    binding.txtnombre.setText("")
                    binding.txtdireccion.setText("")
                    binding.txttelefono.setText("")
                    var s=0
                    for (document in documents) {
                        binding.txtapellido.setText(document.data["apellido"].toString())
                        binding.txtnombre.setText(document.data["nombre"].toString())
                        binding.txtdireccion.setText(document.data["direccion"].toString())
                        binding.txttelefono.setText(document.data["telefono"].toString())
                        s=1
                        break
                        Log.d("mensaje", "${document.id} => ${document.data}")
                    }
                    if (s==0){
                        Toast.makeText(this,"No Existe el Regiustro",Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("mensaje", "Error getting documents: ", exception)
                }

        }*/
    }

}


