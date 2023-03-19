package com.example.studentscoredbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddSubject = findViewById<Button>(R.id.btnAddSubject)
        btnAddSubject.setOnClickListener {
            val db = DBHelper(this, null)
            val etSubjectName = findViewById<EditText>(R.id.etSubjectName)
            val etScore = findViewById<EditText>(R.id.etScore)
            val name = etSubjectName.text.toString()
            val score = etScore.text.toString()
            db.addScore(name, score)
            // Toast to message on the screen
            Toast.makeText(this, name + " added to database", Toast.LENGTH_SHORT).show()
            etSubjectName.text.clear()
            etScore.text.clear()
        }

        val btnPrintSubjects = findViewById<Button>(R.id.btnPrintSubjects)
        btnPrintSubjects.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getAllSubjects()
            cursor!!.moveToFirst()
            val tvSubjectRecord = findViewById<TextView>(R.id.tvSubjectRecord)
            tvSubjectRecord.text = "### Subjects ###\n"
            if (cursor!!.moveToFirst()) {
                tvSubjectRecord.append(cursor.getString(0) + ": " +
                        cursor.getString(1) +
                        "(" + cursor.getString(2) + ")\n")
            }
            while (cursor.moveToNext()) {
                tvSubjectRecord.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)) +
                        ": " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME)) +
                        "(" + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SCORE)) + ")\n")
            }
        }

        val btnDeleteSubject = findViewById<Button>(R.id.btnDeleteSubject)
        btnDeleteSubject.setOnClickListener {
            val db = DBHelper(this, null)
            val subjectName = findViewById<EditText>(R.id.etSubjectName).text.toString()
            val rows = db.deleteSubject(subjectName)
            Toast.makeText(this,
                when (rows) {
                    0 -> "Nothing deleted"
                    1 -> "1 subject deleted"
                    else -> "" // shouldn't happen
                },
                Toast.LENGTH_LONG).show()
        }

        val btnUpdateSubject = findViewById<Button>(R.id.btnUpdateSubject)
        btnUpdateSubject.setOnClickListener {
            val db = DBHelper(this, null)
            val subjectName = findViewById<EditText>(R.id.etSubjectName).text.toString()
            val score = findViewById<EditText>(R.id.etScore).text.toString()
            val rows = db.updateSubject(subjectName, score)
            Toast.makeText(this, "$rows subjects updated", Toast.LENGTH_LONG).show()
        }
    }
}