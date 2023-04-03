package com.example.tallerpractico2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_sign_out){
            FirebaseAuth.getInstance().signOut().also{
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        if(id == R.id.action_exercise_1){
            Toast.makeText(this,"Bienvenido al ejercicio 1",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,EstudiantesActivity::class.java)
            startActivity(intent)
        }
        if(id == R.id.action_exercise_2){
            Toast.makeText(this,"Bienvenido al ejercicio 2",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,EmpleadosActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}