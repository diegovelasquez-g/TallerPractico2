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
import com.example.tallerpractico2.datos.Empleado
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class EmpleadosActivity : AppCompatActivity() {
    //Para realizar la consulta
    var consultaOrdenada: Query = EmpleadosActivity.refEmpleados.orderByChild("nombre")
    var empleados: MutableList<Empleado>? = null
    var listaEmpleados: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)
        inicializar()
    }

    private fun inicializar(){
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaEmpleados = findViewById<ListView>(R.id.ListaEmpleados)

        //Editar registro de notas
        listaEmpleados!!.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, I: Long){
                val intent = Intent(getBaseContext(), Ejercicio2::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", empleados!![i].key)
                intent.putExtra("nombre", empleados!![i].nombre)
                intent.putExtra("salario", empleados!![i].salario)
                startActivity(intent)
            }
        })

        // LongClic eliminar registro
        listaEmpleados!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario si esta seguro
                val ad = AlertDialog.Builder(this@EmpleadosActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    empleados!![position].nombre?.let {
                        refEmpleados.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@EmpleadosActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@EmpleadosActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), Ejercicio2::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("salario", "")
            startActivity(i)
        })
        empleados = ArrayList<Empleado>()

        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Se actualiza la coleccion de empleados
                empleados!!.removeAll(empleados!!)
                for (dato in dataSnapshot.getChildren()) {
                    val empleado: Empleado? = dato.getValue(Empleado::class.java)
                    empleado?.key(dato.key)
                    if (empleado != null) {
                        empleados!!.add(empleado)
                    }
                }
                val adapter = AdaptadorEmpleado(
                    this@EmpleadosActivity,
                    empleados as ArrayList<Empleado>
                )
                listaEmpleados!!.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEmpleados: DatabaseReference = database.getReference("empleados")
    }
}