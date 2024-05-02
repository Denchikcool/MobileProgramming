package com.example.labdatabase

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        createStudentsAndDisplayData()
    }

    private fun createStudentsAndDisplayData() {
        val names = arrayOf("Alice", "Bob", "Charlie", "David", "Emma", "Denis", "Roma", "Misha", "Kate",
            "Nikita", "Vitaly", "Dasha", "Riki", "Lina", "Slark", "Sniper", "Abbadon", "Keeper of the light", "Zeus")
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        for (i in 1..100) {
            val student = Student(names.random(), (50..100).random().toFloat(), (150..200).random().toFloat(), (18..35).random())
            databaseHelper.addStudent(student)
        }

        displayStudentsInTableLayout(tableLayout)
    }

    private fun displayStudentsInTableLayout(tableLayout: TableLayout) {
        val students = databaseHelper.getAllStudents()

        val headerRow = TableRow(this)
        val nameHeader = createTextView("Имя")
        nameHeader.setTypeface(null, Typeface.BOLD)
        val weightHeader = createTextView("Вес")
        weightHeader.setTypeface(null, Typeface.BOLD)
        val heightHeader = createTextView("Рост")
        heightHeader.setTypeface(null, Typeface.BOLD)
        val ageHeader = createTextView("Возраст")
        ageHeader.setTypeface(null, Typeface.BOLD)

        nameHeader.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        headerRow.addView(nameHeader)
        weightHeader.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        headerRow.addView(weightHeader)
        heightHeader.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        headerRow.addView(heightHeader)
        ageHeader.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        headerRow.addView(ageHeader)

        tableLayout.addView(headerRow)

        for(student in students) {
            val row = TableRow(this)
            val nameTextView = createTextView(student.name)
            val weightTextView = createTextView(student.weight.toString())
            val heightTextView = createTextView(student.height.toString())
            val ageTextView = createTextView(student.age.toString())

            nameTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            weightTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            heightTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            ageTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            row.addView(nameTextView)
            row.addView(weightTextView)
            row.addView(heightTextView)
            row.addView(ageTextView)

            tableLayout.addView(row)
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        return textView
    }
}