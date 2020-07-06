package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_principal.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class PrincipalFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragmen
        //weatherTask().execute()
        return inflater.inflate(R.layout.fragment_principal, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passData("T")
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