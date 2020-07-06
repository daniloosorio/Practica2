package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R


class ConfiguracionesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuraciones, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passData("F")
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

    override fun onDestroyView() {
        //findNavController().navigate(R.id.navigation_principal)
        Log.d("mensaje","pase aca")
        super.onDestroyView()
    }

}