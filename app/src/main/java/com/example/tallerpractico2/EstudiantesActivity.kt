package com.example.tallerpractico2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tallerpractico2.datos.Estudiante
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class EstudiantesActivity : AppCompatActivity() {
    //Para realizar la consulta
    var consultaOrdenada: Query = refEstudiantes.orderByChild("nombre")
    var estudiantes: MutableList<Estudiante>? = null
    var listaEstudiantes: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)
        inicializar()
    }

    private fun inicializar(){
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaEstudiantes = findViewById<ListView>(R.id.ListaEstudiantes)

        //Editar registro de notas
        listaEstudiantes!!.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(adapterView: AdapterView<*>?,view: View, i: Int, I: Long){
                val intent = Intent(getBaseContext(), Ejercicio1::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", estudiantes!![i].key)
                intent.putExtra("nombre", estudiantes!![i].nombre)
                intent.putExtra("nota1", estudiantes!![i].nota1)
                intent.putExtra("nota2", estudiantes!![i].nota2)
                intent.putExtra("nota3", estudiantes!![i].nota3)
                intent.putExtra("nota4", estudiantes!![i].nota4)
                intent.putExtra("nota5", estudiantes!![i].nota5)
                startActivity(intent)
            }
        })

        // LongClic eliminar registro
        listaEstudiantes!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario si esta seguro
                val ad = AlertDialog.Builder(this@EstudiantesActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    estudiantes!![position].nombre?.let {
                        refEstudiantes.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@EstudiantesActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@EstudiantesActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), Ejercicio1::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("dui", "")
            startActivity(i)
        })
        estudiantes = ArrayList<Estudiante>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Se actualiza la coleccion de estudiantes
                estudiantes!!.removeAll(estudiantes!!)
                for (dato in dataSnapshot.getChildren()) {
                    val estudiante: Estudiante? = dato.getValue(Estudiante::class.java)
                    estudiante?.key(dato.key)
                    if (estudiante != null) {
                        estudiantes!!.add(estudiante)
                    }
                }
                val adapter = AdaptadorEstudiante(
                    this@EstudiantesActivity,
                    estudiantes as ArrayList<Estudiante>
                )
                listaEstudiantes!!.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEstudiantes: DatabaseReference = database.getReference("estudiantes")
    }
}