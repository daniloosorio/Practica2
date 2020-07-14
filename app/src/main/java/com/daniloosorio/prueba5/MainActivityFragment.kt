package com.daniloosorio.prueba5

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.daniloosorio.prueba5.fragmentos.*
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class MainActivityFragment : AppCompatActivity(),
    PrincipalFragment.OnDataPass,
    NotificationFragment.OnDataPass,
    MusicaFragment.OnDataPass,
    mascotasFragment.OnDataPass,
    DispositivosFragment.OnDataPass,
    PerfilFragment.OnDataPass,
    ConfiguracionesFragment.OnDataPass {
    var backpressedtime: Long = 0
    var back = "F"
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_principal, R.id.navigation_dispositivos, R.id.navigation_mascotas, R.id.navigation_musica, R.id.navigation_notification,
                R.id.navigation_ventilador,R.id.navigation_door,R.id.navigation_led,R.id.navigation_temp,R.id.navigation_nueva_macosta)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    /**********************************************************************************************************/
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
                mAuth.signOut()
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
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
/***
class MainActivityFragment : AppCompatActivity(),
                            PrincipalFragment.OnDataPass,
                            NotificationFragment.OnDataPass,
                            MusicaFragment.OnDataPass,
                            mascotasFragment.OnDataPass,
                            DispositivosFragment.OnDataPass,
                            PerfilFragment.OnDataPass,
                            ConfiguracionesFragment.OnDataPass{
    /***********************Api Weather********************************************/
    val CITY: String = "Medellín, CO"
    val API: String = "d567b93c727fa6ef4d93b26227352f54"
    var LAT: String =""
    var LON: String =""
    /***************************Geolocalizacion************************************/
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    /*****************************************************************************/
    var backpressedtime :Long = 0
    var back="F"
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    //val navController = findNavController(R.id.nav_host_fragment)
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main_fragment)
        /*******************************Geolocalizacion***************************************/
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            getLastLocation()

            weatherTask().execute()
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.navigation_principal, R.id.navigation_dispositivos, R.id.navigation_mascotas, R.id.navigation_musica, R.id.navigation_notification,
                    R.id.navigation_ventilador,R.id.navigation_door,R.id.navigation_led,R.id.navigation_temp,R.id.navigation_nueva_macosta)
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
    }
    /**********************************************Geolocalizacion*********************************************************************/
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), PERMISSION_ID)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        ///findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        //findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                        LAT=location.latitude.toString()
                        LON=location.longitude.toString()
                        Log.d("posision1","$LAT , $LON")
                        //Toast.makeText(this, "${location.latitude.toString()}", Toast.LENGTH_SHORT).show()
                        //Toast.makeText(this, "${location.longitude.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            //findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            //findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
            LAT=mLastLocation.latitude.toString()
            LON=mLastLocation.longitude.toString()
            //Log.d("posision2","$LAT , $LON")
            //Toast.makeText(this@MainActivityFragment, "${mLastLocation.latitude.toString()}", Toast.LENGTH_SHORT).show()
            //Toast.makeText(this@MainActivityFragment, "${mLastLocation.longitude.toString()}", Toast.LENGTH_SHORT).show()
        }
    }
    /************************Api Weather*******************/
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("time", "time")
            /* Showing the ProgressBar, Making the main design GONE */
            //findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            //findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            //findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }
        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?lat=$LAT&lon=$LON&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.status2).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp2).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            }catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }
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
                mAuth.signOut()
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
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

**********/