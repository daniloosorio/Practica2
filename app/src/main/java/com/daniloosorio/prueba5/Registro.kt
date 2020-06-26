package com.daniloosorio.prueba5

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class Registro : AppCompatActivity() {
    private var fecha :String=""
    private var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //////////tomar datos del calendario//////////
        val dateSetListener= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_YEAR,dayOfMonth)
            val format="MM/dd/yyyy"
            val simpleDateFormat=SimpleDateFormat(format,Locale.US)
            fecha=simpleDateFormat.format(cal.time).toString()
            tv_fecha.text=fecha
        }
///////////boton calendario////////////
        ib_calendario.setOnClickListener{
            DatePickerDialog(this,dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
        }
////////////boton guardar///////////////////
        bt_guardar.setOnClickListener {
            val nombre = ed_nombre.text.toString()
            val apellido=ed_apellido.text.toString()
            val cedula=ed_cedula.text.toString()
            val telefono= ed_telefono.text.toString()
            val correo= ed_correo.text.toString()
            val contrasena = ed_contrasena.text.toString()
            val Rcontrasena= ed_Rcontrasena.text.toString()
            val genero = if(rb_mujer.isChecked)"Femenino" else "Masculino"
            val ciudadnacimiento = sp_ciudad.selectedItem.toString()
            var terminos="NoAcepto"
            if(cb_terminos.isChecked) terminos ="Acepto"
            var ver_vacios =vacios(nombre,apellido,cedula,telefono,correo,contrasena,Rcontrasena,ciudadnacimiento,fecha,terminos)
            if(ver_vacios){
                if(contrasena.length>6){
                    if(contrasena==Rcontrasena){
///////////////////////////////cambiar a actividad login y salvar los datos///////////////////////
                        val intent = Intent(this, login::class.java)
                        intent.putExtra("correo",correo)
                        intent.putExtra("contrasena",contrasena)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{ Toast.makeText(this, "Las Cotraseñas no son iguales !!!", Toast.LENGTH_SHORT).show()}
                }else{Toast.makeText(this, "Contraseña muy corta debe ser de mas de 6 caracterers.", Toast.LENGTH_SHORT).show()}
            }
        }
    }

//////////////////////////////////funcion para vacios////////////////////////////////////////
    fun vacios(nombre :String, apellido: String, cedula : String,
               telefono :String ,correo : String,contrasena : String,
               rcontrasena: String,paisdenacimiento: String, fecha_nacimiento: String, terminos: String): Boolean {
        if (nombre.isNullOrEmpty()){ Toast.makeText(this, "Escribir Nombre.", Toast.LENGTH_SHORT).show()
        }else if (apellido.isNullOrEmpty()){ Toast.makeText(this, "Escribir Apellido.", Toast.LENGTH_SHORT).show()
        }else if (cedula.isNullOrEmpty()) { Toast.makeText(this, "Escribir Cedula !!!", Toast.LENGTH_SHORT).show()
        }else if (telefono.isNullOrEmpty()){ Toast.makeText(this, "Escribir Telefono !!!", Toast.LENGTH_SHORT).show()
        }else if (correo.isNullOrEmpty()){ Toast.makeText(this, "Escribir Correo !!!", Toast.LENGTH_SHORT).show()
        }else if (contrasena.isNullOrEmpty()){ Toast.makeText(this, "Escribir Contrasena !!!", Toast.LENGTH_SHORT).show()
        }else if (rcontrasena.isNullOrEmpty()){ Toast.makeText(this, "Repetir Contrasena !!!", Toast.LENGTH_SHORT).show()
        }else if (paisdenacimiento=="Pais de Nacimiento"){ Toast.makeText(this, "Seleccionar un pais !!!", Toast.LENGTH_SHORT).show()
        }else if (fecha_nacimiento.isNullOrEmpty()){ Toast.makeText(this, "Escribir  Fecha de Nacimiento !!!", Toast.LENGTH_SHORT).show()
        }else if (terminos=="NoAcepto"){ Toast.makeText(this, "Debe aceptar los terminos y condiciones", Toast.LENGTH_SHORT).show()
        }else return true
        return false
    }
}
