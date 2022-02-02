package com.example.datos

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.datos.databinding.ActivityFrmContactoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class FrmContacto : AppCompatActivity() {
    lateinit var binding: ActivityFrmContactoBinding
    val db= FirebaseFirestore.getInstance()
    var rutaimagen=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFrmContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Registro de Contactos")

        binding.btnbuscar.setOnClickListener {
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
                        var vinculo=document.data["imagen"].toString()
                        binding.imgfoto.setImageBitmap(null)
                        Toast.makeText(this,vinculo.toString().length.toString(),3000).show()
                        if (vinculo.toString().length > 0) {
                            Picasso.get().load(vinculo).into(binding.imgfoto)
                        }
                        s=1
                        break
                        Log.d("mensaje", "${document.id} => ${document.data}")
                    }
                    if (s==0){
                        binding.imgfoto.setImageBitmap(null)
                        Toast.makeText(this,"No Existe el Regiustro",Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("mensaje", "Error getting documents: ", exception)
                }
        }

        binding.btncerrar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
        binding.btnguardar.setOnClickListener {
            val contacto= db.collection("Contactos")
            var apellido=binding.txtapellido.text.toString()
            var nombre=binding.txtnombre.text.toString()
            var direccion=binding.txtdireccion.text.toString()
            var telefono=binding.txttelefono.text.toString()


            //----- Levantar Foto -------
            if(foto==null){
                guardarfoto("")
            }
            if(foto!=null){
                val filename=UUID.randomUUID().toString()
                val ref=FirebaseStorage.getInstance().getReference("/fotos/$filename")
                ref.putFile(foto!!).addOnSuccessListener {
                    //Log.d("Register","Guardo la imagen: ${it.metadata?.path} ")
                    ref.downloadUrl.addOnSuccessListener {
                        Log.e("Register","Ruta: ${it} ")
                        guardarfoto(it.toString())
                    }
                }
            }

        }
        binding.btnfoto.setOnClickListener {
            val g=Intent(
                Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(g,1001)
        }

    }
    fun guardarfoto(ruta:String){
        val contacto= db.collection("Contactos")
        var apellido=binding.txtapellido.text.toString()
        var nombre=binding.txtnombre.text.toString()
        var direccion=binding.txtdireccion.text.toString()
        var telefono=binding.txttelefono.text.toString()
        var reg= contacto(apellido,nombre,direccion,telefono,ruta)
        contacto.add(
            reg
        ).addOnSuccessListener {
            Log.d("mensaje", "Added document with ID ${it.id}")
        }.addOnFailureListener { exception ->
            Log.w("mensaje", "Error adding document $exception")
        }
    }

    var foto:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1001) {
            binding.imgfoto.setImageURI(data?.data)
            foto=data?.data
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,foto)
            val bitmapDraw=BitmapDrawable(bitmap)
        }
    }
}