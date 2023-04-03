package com.example.tallerpractico2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpractico2.datos.Empleado

class AdaptadorEmpleado(private val context: Activity, var empleados: List<Empleado>):
    ArrayAdapter<Empleado?>(context, R.layout.empleado_layaout, empleados) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        rowview = view ?: layoutInflater.inflate(R.layout.empleado_layaout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvSalario = rowview!!.findViewById<TextView>(R.id.tvSalario)
        tvNombre.text = "Nombre : " + empleados[position].nombre
        tvSalario.text = "Salario : " + empleados[position].salario
        return rowview
    }
}