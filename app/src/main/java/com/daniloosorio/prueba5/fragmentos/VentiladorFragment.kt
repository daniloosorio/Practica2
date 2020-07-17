package com.daniloosorio.prueba5.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.daniloosorio.prueba5.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_led.*
import kotlinx.android.synthetic.main.fragment_ventilador.*


class VentiladorFragment : Fragment() {
    val database = FirebaseDatabase.getInstance()
    val myRef =database.getReference("dispositivos")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ventilador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var estado=switch_fan
        estado?.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "ON" else "OFF"
            ActualizarEnFireBase(1,message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                // Display the current progress of SeekBar
                ActualizarEnFireBase(2,progress.toString())
                Toast.makeText(context, "Progreso $progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(context,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(context,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun ActualizarEnFireBase(id: Int,message: String) {
        // val database = FirebaseDatabase.getInstance()
        // val myRef =database.getReference("deudores")
        val chilUpdate = HashMap<String, Any>()
        if(id==1) {
            chilUpdate["estado"] = message
            myRef.child("ventilador").updateChildren(chilUpdate)
            //myRef.updateChildren(chilUpdate)
        }else{
            chilUpdate["velocidad"] = message
            myRef.child("ventilador").updateChildren(chilUpdate)
            //myRef.updateChildren(chilUpdate)
        }
    }

}