package com.daniloosorio.prueba5.fragmentos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcinmoskala.arcseekbar.ArcSeekBar
import com.marcinmoskala.arcseekbar.ProgressListener
import kotlinx.android.synthetic.main.fragment_dispositivos.*
import kotlinx.android.synthetic.main.fragment_led.*

class ledFragment : Fragment() {
    val database = FirebaseDatabase.getInstance()
    val myRef =database.getReference("dispositivos")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_led, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            var estado=switch_led
        buscarEnFirebase("luz")
            estado?.setOnCheckedChangeListener { _, isChecked ->
                val message = if (isChecked) "ON" else "OFF"
                ActualizarEnFireBase(message)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                textView5.text=seekArc.progress.toString()
        }
        seekArc.setProgressGradient(Color.GREEN, Color.BLUE, Color.YELLOW)
        seekArc.setOnClickListener{
            Toast.makeText(context, "hfdgdfsgh", Toast.LENGTH_SHORT).show()
        }

    }
    private fun ActualizarEnFireBase(message: String) {
        // val database = FirebaseDatabase.getInstance()
        // val myRef =database.getReference("deudores")
        val chilUpdate = HashMap <String,Any>()
        chilUpdate ["luz"]=message
        myRef.updateChildren(chilUpdate)
    }
    private fun buscarEnFirebase(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("dispositivos")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var estado =snapshot.child("luz").value.toString()
                if(estado=="ON"){switch_led.setChecked(true)}else{switch_led.setChecked(false)}
                Toast.makeText(context, "$estado", Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}
