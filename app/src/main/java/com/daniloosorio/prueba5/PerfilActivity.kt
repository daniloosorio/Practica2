package com.daniloosorio.prueba5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daniloosorio.prueba5.remote.UserRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_perfil.*

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val usuario: FirebaseUser? = mAuth.currentUser
    val correo = usuario?.email
    buscarEnFirebase(correo!!)
}

    private fun buscarEnFirebase(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val usuario = datasnapshot.getValue(UserRemote::class.java)
                    if (usuario?.correo == correo) {
                        Log.d("TAG","${usuario.correo}")
                        tv_nombre.text=usuario.nombre
                        tv_apellido.text=usuario.apellido
                        tv_cedula.text=usuario.cedula
                        tv_telefono.text=usuario.telefono
                        tv_correo.text=usuario.correo
                        tv_genero.text=usuario.genero
                        tv_ciudad.text=usuario.ciudaddenacimiento
                        tv_fecha.text=usuario.fecha
                        tv_user1.text="ND"
                        tv_user2.text="ND"
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}