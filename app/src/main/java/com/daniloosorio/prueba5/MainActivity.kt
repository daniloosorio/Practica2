package com.daniloosorio.prueba5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var correo : String
    lateinit var contrasena : String
    var backpressedtime :Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //////////////////////////recibo datos de login/////////////////////////////////
        var datos_login =intent.extras
        correo = datos_login?.getString("correo").toString()
        contrasena= datos_login?.getString("contrasena").toString()
        tv_usuario.text=correo
    }
///////////////////////////presionar de nuevo para salir////////////////////////////////
    override fun onBackPressed() {

        if(backpressedtime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            return
        }else{
            Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show()
        }
        backpressedtime = System.currentTimeMillis()

    }
////////////////////////////////////menu overflow///////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_perfil -> {
                tv_mensaje.text="perfil no disponible"
            }
            R.id.navigation_configuraciones -> {
                tv_mensaje.text="configuraciones no disponibles"
            }
            R.id.cerrar -> {
               val intent = Intent(this, login::class.java)
                intent.putExtra("correo",correo)
                intent.putExtra("contrasena",contrasena)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
