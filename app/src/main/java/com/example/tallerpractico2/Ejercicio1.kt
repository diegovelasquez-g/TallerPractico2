package com.example.tallerpractico2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tallerpractico2.datos.Estudiante
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Ejercicio1 : AppCompatActivity() {
    private var edtNombre: EditText? = null
    private var edtNota1: EditText? = null
    private var edtNota2: EditText? = null
    private var edtNota3: EditText? = null
    private var edtNota4: EditText? = null
    private var edtNota5: EditText? = null
    private var name = ""
    private var key = ""
    private var eval1 = ""
    private var eval2 = ""
    private var eval3 = ""
    private var eval4 = ""
    private var eval5 = ""
    private var accion = ""
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)
        inicializar()

        val prom = findViewById<Button>(R.id.btnCalcular)
        val name = findViewById<EditText>(R.id.txtAlumno)
        val eval1 = findViewById<EditText>(R.id.txtNota1)
        val eval2 = findViewById<EditText>(R.id.txtNota2)
        val eval3 = findViewById<EditText>(R.id.txtNota3)
        val eval4 = findViewById<EditText>(R.id.txtNota4)
        val eval5 = findViewById<EditText>(R.id.txtNota5)
        val result = findViewById<TextView>(R.id.txtResultado)

        prom.setOnClickListener {
            val student: String = name.text.toString()

            if (TextUtils.isEmpty(student)) {
                Toast.makeText(this, "Por favor ingrese el nombre del alumno", Toast.LENGTH_SHORT)
                    .show()
            } else if (TextUtils.isEmpty((eval1.text.toString())) || TextUtils.isEmpty((eval2.text.toString())) || TextUtils.isEmpty(
                    (eval3.text.toString())
                ) || TextUtils.isEmpty((eval4.text.toString())) || TextUtils.isEmpty((eval5.text.toString()))
            ) {
                Toast.makeText(this, "Por favor complete los campos de notas", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val grade1: Double = eval1.text.toString().toDouble()
                val grade2: Double = eval2.text.toString().toDouble()
                val grade3: Double = eval3.text.toString().toDouble()
                val grade4: Double = eval4.text.toString().toDouble()
                val grade5: Double = eval5.text.toString().toDouble()
                val ans: Double = (grade1 + grade2 + grade3 + grade4 + grade5) / 5
                val ansRound: Double = Math.round(ans * 100.0) / 100.0
                var res = "Reprobado"
                if (ans >= 6) {
                    res = "Aprobado"
                }
                result.setText("El promedio del alumno " + student + " es: " + ansRound.toString())
                Toast.makeText(this, res, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.txtAlumno)
        edtNota1 = findViewById<EditText>(R.id.txtNota1)
        edtNota2 = findViewById<EditText>(R.id.txtNota2)
        edtNota3 = findViewById<EditText>(R.id.txtNota3)
        edtNota4 = findViewById<EditText>(R.id.txtNota4)
        edtNota5 = findViewById<EditText>(R.id.txtNota5)

        val edtNombre = findViewById<EditText>(R.id.txtAlumno)
        val edtNota1 = findViewById<EditText>(R.id.txtNota1)
        val edtNota2 = findViewById<EditText>(R.id.txtNota2)
        val edtNota3 = findViewById<EditText>(R.id.txtNota3)
        val edtNota4 = findViewById<EditText>(R.id.txtNota4)
        val edtNota5 = findViewById<EditText>(R.id.txtNota5)

        // Obteniendo datos previos del estudiantes
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            edtNota1.setText(intent.getStringExtra("nota1").toString())
        }
        if (datos != null) {
            edtNota2.setText(intent.getStringExtra("nota2").toString())
        }
        if (datos != null) {
            edtNota3.setText(intent.getStringExtra("nota3").toString())
        }
        if (datos != null) {
            edtNota4.setText(intent.getStringExtra("nota4").toString())
        }
        if (datos != null) {
            edtNota5.setText(intent.getStringExtra("nota5").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }
    }

    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val nota1: String = edtNota1?.text.toString()
        val nota2: String = edtNota2?.text.toString()
        val nota3: String = edtNota3?.text.toString()
        val nota4: String = edtNota4?.text.toString()
        val nota5: String = edtNota5?.text.toString()
        database = FirebaseDatabase.getInstance().getReference("estudiantes")
        // Se forma objeto estudiante
        val estudiante = Estudiante(nombre, nota1, nota2, nota3, nota4, nota5)
        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(estudiante).addOnSuccessListener {
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
            val estudiantesValues = estudiante.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to estudiantesValues
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