package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniloosorio.prueba5.R
import kotlinx.android.synthetic.main.fragment_musica.*
import java.io.IOException

class MusicaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_musica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passData("F")
        val mediaPlayer:MediaPlayer= MediaPlayer.create(activity,R.raw.acdc)
        play.setOnClickListener(){
            mediaPlayer.start()
        }
        pause.setOnClickListener(){
            if(mediaPlayer.isPlaying)
                mediaPlayer.pause()
            else
                mediaPlayer.start()
        }
        stop.setOnClickListener(){
            mediaPlayer.stop()
            try {
                mediaPlayer.prepare()
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
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