package com.example.tallerpractico2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tallerpractico2.datos.Estudiante

class AdaptadorEstudiante(private val context: Activity, var estudiantes: List<Estudiante>):
    ArrayAdapter<Estudiante?>(context, R.layout.estudiante_layaout, estudiantes) {
        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            // para formar a cada item que se visualizara en la lista personalizada
            val layoutInflater = context.layoutInflater
            var rowview: View? = null
            rowview = view ?: layoutInflater.inflate(R.layout.estudiante_layaout, null)
            val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
            tvNombre.text = "Nombre : " + estudiantes[position].nombre
            return rowview
        }
}