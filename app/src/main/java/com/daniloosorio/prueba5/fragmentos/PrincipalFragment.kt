package com.daniloosorio.prueba5.fragmentos

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_principal.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class PrincipalFragment : Fragment() {
    var temperatura = "g"
    val PERMISSION_ID = 42
    var latitude =35.0789642
    var longitude =-85.628049
    val API: String = "d567b93c727fa6ef4d93b26227352f54"
    var CITY: String = "dhaka,bd"
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var dataPasser: OnDataPass
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastLocation()
        temp2.text = "$temperatura"
        passData("T")
    }

    private fun miCiudad() {
        val geocoder: Geocoder
        val direccion: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())
        direccion = geocoder.getFromLocation(latitude, longitude, 1) // 1 representa la cantidad de resultados a obtener
        //val address: String = direccion[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        val city: String = direccion[0].locality
        address.text = city + ", CO"
        CITY=city + ", CO"
        weatherTask().execute()
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            loader.visibility = View.VISIBLE
            mainContainer.visibility = View.GONE
            errorText.visibility = View.GONE
        }
        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
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
                val wind1 = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure1 = main.getString("pressure")
                val humidity1 = main.getString("humidity")

                val sunrise1:Long = sys.getLong("sunrise")
                val sunset1:Long = sys.getLong("sunset")
                val windSpeed = wind1.getString("speed")
                val weatherDescription = weather.getString("description")

                val address1 = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                temp2.text= temp
               address.text = address1
                updated_at.text =  updatedAtText
                status2.text = weatherDescription.capitalize()
                temp2.text = temp
                temp_min.text = tempMin
                temp_max.text = tempMax
                sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise1*1000))
                sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset1*1000))
                wind.text = windSpeed
                pressure.text = pressure1
                humidity.text = humidity1

                /* Views populated, Hiding the loader, Showing the main design */
                loader.visibility = View.GONE
                mainContainer.visibility = View.VISIBLE

            } catch (e: Exception) {
                loader.visibility = View.GONE
                errorText.visibility = View.VISIBLE
            }

        }
    }
    interface OnDataPass {
        fun onDataPass(data: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: String) {
        dataPasser.onDataPass(data)
    }

  @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        Toast.makeText(requireContext(), "${location.latitude}", Toast.LENGTH_SHORT).show()
                       latitude=location.latitude
                        Toast.makeText(requireContext(), "${location.longitude}", Toast.LENGTH_SHORT).show()
                       longitude=location.longitude
                        miCiudad()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Toast.makeText(requireContext(), "${mLastLocation.latitude}", Toast.LENGTH_SHORT).show()
            latitude=mLastLocation.latitude
            Toast.makeText(requireContext(), "${mLastLocation.longitude}", Toast.LENGTH_SHORT).show()
            longitude=mLastLocation.longitude
            miCiudad()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() { ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), PERMISSION_ID)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}