package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R
import kotlinx.android.synthetic.main.fragment_principal.*



class PrincipalFragment : Fragment(){
     var temperatura="g"
    lateinit var dataPasser: OnDataPass
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        temp2.setText("$temperatura")
        passData("T")
    }
    interface OnDataPass {
        fun onDataPass(data: String)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }
    fun passData(data: String){
        dataPasser.onDataPass(data)
    }

}