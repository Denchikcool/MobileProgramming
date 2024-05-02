package com.example.labdatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "Students.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_HEIGHT = "height"
        private const val COLUMN_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_WEIGHT REAL, $COLUMN_HEIGHT REAL, $COLUMN_AGE INTEGER)"
        db.execSQL(createTable)
    }
    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("DELETE FROM $TABLE_NAME")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_WEIGHT, student.weight)
            put(COLUMN_HEIGHT, student.height)
            put(COLUMN_AGE, student.age)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_AGE", null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val weight = cursor.getFloat(cursor.getColumnIndex(COLUMN_WEIGHT))
            val height = cursor.getFloat(cursor.getColumnIndex(COLUMN_HEIGHT))
            val age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
            students.add(Student(name, weight, height, age))
        }
        cursor.close()
        return students
    }
}