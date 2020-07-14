package com.daniloosorio.prueba5

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class login : AppCompatActivity() {
    override fun onStart() {
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        super.onStart()
        val user = mAuth.currentUser
        if(user !=null){
            startActivity(Intent(this, MainActivityFragment::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_registro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        bt_entrar.setOnClickListener {
            var usuario=ed_usuario.text.toString()
            val contrasena = ed_contrasena.text.toString()
            val mAuth :FirebaseAuth =FirebaseAuth.getInstance()
            if(vacios(usuario,contrasena)){
                mAuth.signInWithEmailAndPassword(usuario, contrasena)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivityFragment::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            //goToMain(usuario,contrasena)
        }
    }
    fun vacios (usuario: String,contrasena: String): Boolean{
        if (usuario.isNullOrEmpty()){
            Toast.makeText(this, "Escribir Correo !!!", Toast.LENGTH_SHORT).show();
            // Focus en jugar y abrir el Teclado
            ed_usuario.requestFocus()
     }else if (contrasena.isNullOrEmpty()){
            Toast.makeText(this, "Escribir Contrasena !!!", Toast.LENGTH_SHORT).show();
        // Focus en contrasena y abrir el Teclado
            ed_contrasena.requestFocus();
        }else return true
        return false
        }
}
