package com.daniloosorio.prueba5.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.prueba5.R
import com.daniloosorio.prueba5.remote.MascotaRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_dispositivos.*
import kotlinx.android.synthetic.main.fragment_mascotas.*
import kotlinx.android.synthetic.main.fragment_ventilador.*

class mascotasFragment : Fragment() {
        private val mascotasList :MutableList<MascotaRemote> = mutableListOf()
    private lateinit var mascotasAdapter : MascotasRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mascotas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passData("F")
        cargarMascotas()
        rv_mascotas.layoutManager=LinearLayoutManager(
            requireContext(),RecyclerView.VERTICAL,
            false
        )
        rv_mascotas.setHasFixedSize(true)
        mascotasAdapter= MascotasRVAdapter(mascotasList as ArrayList<MascotaRemote>)
        rv_mascotas.adapter =mascotasAdapter
        bt_nmascota.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_mascotas_to_navigation_nueva_macosta)
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
    private fun cargarMascotas(){
        val database =FirebaseDatabase.getInstance()
        val myRef = database.getReference("Mascotas")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val mascota = datasnapshot.getValue(MascotaRemote::class.java)
                    mascotasList.add(mascota!!)
                }
                mascotasAdapter.notifyDataSetChanged()
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

}