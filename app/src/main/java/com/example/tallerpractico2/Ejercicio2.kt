package com.example.tallerpractico2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tallerpractico2.datos.Empleado
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Ejercicio2 : AppCompatActivity() {
    //Declarando variables
    private var edtNombre: EditText? = null
    private var edtSalario: EditText? = null
    private var key = ""
    private var accion = ""
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)
        inicializar()

        val salario = findViewById<EditText>(R.id.txtSalario)
        val isss = findViewById<TextView>(R.id.txtISSS)
        val renta = findViewById<TextView>(R.id.txtRenta)
        val total = findViewById<TextView>(R.id.txtTotal)
        val afp = findViewById<TextView>(R.id.txtAFP)
        val calcular = findViewById<Button>(R.id.btnCalcular)

        calcular.setOnClickListener{
            val sal: Double = salario.text.toString().toDouble()
            val afpRound: Double = Math.round((sal*0.04)*100.0)/100.0
            val rentaRound: Double = Math.round((sal*0.05)*100.0)/100.0
            val isssRound: Double = Math.round((sal*0.03)*100.0)/100.0
            val res: Double = sal - afpRound - rentaRound - isssRound
            val resRound: Double = Math.round(res*100.0)/100.0

            isss.setText("ISSS: $"+isssRound.toString())
            afp.setText("AFP: $"+afpRound.toString())
            renta.setText("Renta: $"+rentaRound.toString())
            total.setText("Total: $"+resRound.toString())
        }
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.txtNombre)
        edtSalario = findViewById<EditText>(R.id.txtSalario)

        val edtNombre = findViewById<EditText>(R.id.txtNombre)
        val edtSalario = findViewById<EditText>(R.id.txtSalario)

        // Obteniendo datos previos del empleados
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            edtSalario.setText(intent.getStringExtra("salario").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }
    }

    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val salario: String = edtSalario?.text.toString()
        database = FirebaseDatabase.getInstance().getReference("empleados")
        // Se forma objeto empleado
        val empleado = Empleado(nombre, salario)
        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(empleado).addOnSuccessListener {
                Toast.makeText(this, "Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
            }
        } else // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this, "Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val empleadosValues = empleado.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to empleadosValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this, "Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}