package com.daniloosorio.prueba5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.daniloosorio.prueba5.fragmentos.*

class MainActivityFragment : AppCompatActivity(),
                            PrincipalFragment.OnDataPass,
                            NotificationFragment.OnDataPass,
                            MusicaFragment.OnDataPass,
                            mascotasFragment.OnDataPass,
                            DispositivosFragment.OnDataPass,
                            PerfilFragment.OnDataPass,
                            ConfiguracionesFragment.OnDataPass{
    var backpressedtime :Long = 0
    var back="F"
    lateinit var correo : String
    lateinit var contrasena : String
    //val navController = findNavController(R.id.nav_host_fragment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        var datosRecibidos =intent.extras
        correo = datosRecibidos?.getString("correo").toString()
        contrasena= datosRecibidos?.getString("contrasena").toString()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_principal, R.id.navigation_dispositivos, R.id.navigation_mascotas, R.id.navigation_musica, R.id.navigation_notification,
                R.id.navigation_ventilador,R.id.navigation_door,R.id.navigation_led,R.id.navigation_temp,R.id.navigation_nueva_macosta)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.perfil-> {
                val intent = Intent(this, PerfilActivity::class.java)
                //findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_perfil)
                startActivity(intent)
            }
            R.id.configuraciones -> {
                val intent = Intent(this, ConfiguracionesActivity::class.java)
                startActivity(intent)
               // findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_perfil)
                //navController.popBackStack(R.id.navigation_perfil,true)
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

    /*
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_dispositivos -> {
                Toast.makeText(this, "holaaa1", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notification -> {
                Toast.makeText(this, "holaaa2", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_principal ->{
                back=true
                Toast.makeText(this, "holaaa3", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_musica -> {
                Toast.makeText(this, "holaaa3", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mascotas -> {
                Toast.makeText(this, "holaaa3", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
*/
    override fun onBackPressed() {

        if (back=="T") {
            if (backpressedtime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
                return
            } else {
                Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backpressedtime = System.currentTimeMillis()
        }else super.onBackPressed()
    }
    override fun onDataPass(data: String) {
        //Log.d("LOG","hello " + data)
        back=data
    }
}


