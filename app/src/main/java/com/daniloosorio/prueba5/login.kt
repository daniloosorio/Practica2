package com.daniloosorio.prueba5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var datosRecibidos =intent.extras
        var correo_recibido = datosRecibidos?.getString("correo")
        var contrasena_recibida= datosRecibidos?.getString("contrasena")

        tv_registro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        bt_entrar.setOnClickListener {
            var usuario=ed_usuario.text.toString()
            val contrasena = ed_contrasena.text.toString()
            if(vacios(usuario,contrasena)){
                if(usuario==correo_recibido && contrasena==contrasena_recibida) {
                    goToMain(correo_recibido,contrasena_recibida)
                }else{Toast.makeText(this, "Correo o contraseña incorrecto", Toast.LENGTH_SHORT).show()}
            }
        }
    }
    private fun goToMain(correo_r:String,contrasena_r:String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("correo",correo_r)
        intent.putExtra("contrasena", contrasena_r)
        startActivity(intent)
        finish()
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
