package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.daniloosorio.prueba5.R
import kotlinx.android.synthetic.main.fragment_dispositivos.*

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
}