package com.example.p2gps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.p2gps.start
import com.example.p2gps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setup()
    }

    private fun setup() {

        binding.btnStart.setOnClickListener {

            // Agregar un Toast para mostrar un mensaje
            val mensaje = "¡Hola, este es un Toast!"
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            val intent = Intent(this,start::class.java)
            startActivity(intent)
        }

    }


}