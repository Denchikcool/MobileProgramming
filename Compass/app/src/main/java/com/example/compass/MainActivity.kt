package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import kotlin.text.Typography.degree

class MainActivity : AppCompatActivity(), SensorEventListener {
    var manager: SensorManager? = null
    var currentDegree: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degreeNumber: Int = event?.values?.get(0)?.toInt()!!
        var tvDegree: TextView = findViewById(R.id.tvDegree)
        var imgCompass: ImageView = findViewById(R.id.imgCompass)


        tvDegree.text = degreeNumber.toString() + " degrees"
        val rotationAnimation = RotateAnimation(currentDegree.toFloat(), (-degreeNumber).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotationAnimation.duration = 210
        rotationAnimation.fillAfter = true
        currentDegree = -degreeNumber
        imgCompass.startAnimation(rotationAnimation)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}