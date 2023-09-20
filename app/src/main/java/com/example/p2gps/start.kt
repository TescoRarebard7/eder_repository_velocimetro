package com.example.p2gps
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class start: AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var speedSensor: Sensor? = null
    private lateinit var speedTextView: TextView
    private lateinit var Momi: TextView
    private var isMtS:Boolean  = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        speedTextView = findViewById(R.id.velocidad)
        Momi = findViewById(R.id.MoMi)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        isMtS = false

        // Verificar y solicitar permisos de ubicación si es necesario
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        // Obtener el sensor de velocidad
        speedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        Momi.setOnClickListener {
            if (isMtS.equals(false)){
                isMtS = true
                Momi.text = "M/S"
            }else{
                isMtS = false
                Momi.text = "Mi/S"
            }
        }
    }



    override fun onResume() {
        super.onResume()

        // Registrar el listener del sensor de velocidad
        speedSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()

        // Detener la lectura del sensor de velocidad
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se utiliza en este ejemplo
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            var speed: Float = 0.0f
            var formattedSpeed = ""

            if (isMtS) {
                speed = if (event.values[0] < 0) 0f else event.values[0]
                formattedSpeed = "%.2f".format(speed)
            } else {
                val metersPerSecond = if (event.values[0] < 0) 0f else event.values[0]
                speed = metersPerSecond * 0.000621371f
                formattedSpeed = "%.5f".format(speed)
            }

            runOnUiThread {
                speedTextView.text = "$formattedSpeed"
            }
        }
    }



}
