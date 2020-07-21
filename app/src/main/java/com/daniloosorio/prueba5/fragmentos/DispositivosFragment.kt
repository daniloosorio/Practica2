package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.daniloosorio.prueba5.R
import com.daniloosorio.prueba5.remote.DispositivosRemote
import com.daniloosorio.prueba5.remote.MascotaRemote
import com.daniloosorio.prueba5.remote.UserRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_dispositivos.*
import kotlinx.android.synthetic.main.fragment_led.*
import kotlinx.android.synthetic.main.fragment_ventilador.*

class DispositivosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dispositivos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passData("F")
        buscarEnFirebase("luz")
        buscarEnFirebase2("estado")
        var estado=sw_fan
        estado?.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "ON" else "OFF"
            ActualizarEnFireBase(message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        }
        var estado2=sw_led
        estado2?.setOnCheckedChangeListener { _, isChecked ->
            val message2 = if (isChecked) "ON" else "OFF"
            ActualizarEnFireBase2(message2)
            Toast.makeText(requireContext(), message2, Toast.LENGTH_SHORT).show()

        }
        ib_led.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dispositivos_to_navigation_led)
        }
        ib_door.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dispositivos_to_navigation_door)
        }
        ib_temp.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dispositivos_to_navigation_temp)
        }
        ib_ventilador.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dispositivos_to_navigation_ventilador)
        }
        //Toast.makeText(context, "sadgfdgd", Toast.LENGTH_SHORT).show()
        //Log.d("hola", "holiii")
    }

    interface OnDataPass {
        fun onDataPass(data: String)
    }

    lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }
    fun passData(data: String){
        dataPasser.onDataPass(data)
    }
    private fun buscarEnFirebase(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("dispositivos")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var estado =snapshot.child("luz").value.toString()
                if(estado=="ON"){sw_led.setChecked(true)}else{sw_led.setChecked(false)}
                Toast.makeText(context, "$estado", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
    private fun buscarEnFirebase2(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("dispositivos").child("ventilador")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var estado =snapshot.child("estado").value.toString()
                if(estado=="ON"){sw_fan.setChecked(true)}else{sw_fan.setChecked(false)}
                Toast.makeText(context, "$estado", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
    private fun ActualizarEnFireBase(message: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("dispositivos")
        val chilUpdate = HashMap<String, Any>()
            chilUpdate["estado"] = message
            myRef.child("ventilador").updateChildren(chilUpdate)
            //myRef.updateChildren(chilUpdate)
    }
    private fun ActualizarEnFireBase2(message: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("dispositivos")
        val chilUpdate = HashMap<String, Any>()
        chilUpdate["luz"] = message
        myRef.updateChildren(chilUpdate)
        //myRef.updateChildren(chilUpdate)
    }
}