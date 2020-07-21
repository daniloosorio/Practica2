package com.daniloosorio.prueba5.fragmentos

import android.os.Bundle
import android.text.BoringLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.prueba5.R
import com.daniloosorio.prueba5.remote.MascotaRemote
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_nmascotas.*

class NmascotasFragment : Fragment(){
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference =database.getReference("Mascotas")
    val id :String? = myRef.push().key
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nmascotas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_guardar.setOnClickListener {
            if(vacios()){
                val nombre =et_nombre.text.toString()
                val tipo=sp_mascotas.selectedItem.toString()
                val edad =et_edad.text.toString()
                val peso =et_peso.text.toString()
                val intervalos = et_intervalos.text.toString()
                val l = cb_L.isChecked
                val m = cb_M.isChecked
                val w = cb_W.isChecked
                val j = cb_J.isChecked
                val v = cb_V.isChecked
                val s = cb_S.isChecked
                val d = cb_D.isChecked
                crearMascotaEnBaseDeDatos(id,nombre,tipo,edad,peso,intervalos,l,m,w,j,v,s,d)
                Toast.makeText(context, "mascota agregada", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_navigation_nueva_macosta_to_navigation_mascotas)
                getActivity()?.onBackPressed()
            }
        }
    }

    private fun vacios(): Boolean {
    if(sp_mascotas.selectedItem.toString()=="Seleccione el tipo de mascota v" ||
            et_nombre.text.isNullOrEmpty()||
        et_edad.text.isNullOrEmpty()||
        et_peso.text.isNullOrEmpty()||
        et_intervalos.text.isNullOrEmpty()){
        Toast.makeText(context, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        return false
    }else if(cb_L.isChecked||cb_M.isChecked||cb_W.isChecked||cb_J.isChecked||cb_V.isChecked||cb_S.isChecked||cb_D.isChecked){
        return true
    }else{
        Toast.makeText(context, "Debe seleccionar minimo un dia", Toast.LENGTH_SHORT).show()
        return false
    }
        return false
    }

    private fun crearMascotaEnBaseDeDatos(
        id: String?,
        nombre: String,
        tipo: String,
        edad: String,
        peso: String,
        intervalos: String,
        l: Boolean,
        m: Boolean,
        w: Boolean,
        j: Boolean,
        v: Boolean,
        s: Boolean,
        d: Boolean
        //dias : String
    ) {
        val mascota = MascotaRemote(
            id,
            nombre,
            tipo,
            edad,
            peso,
            intervalos,
            l,
            m,
            w,
            j,
            v,
            s,
            d
         //   dias
        )
        myRef.child(id!!).setValue(mascota)
        Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()

    }
}