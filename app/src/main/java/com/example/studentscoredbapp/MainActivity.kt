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
            val etSubjectScore = findViewById<EditText>(R.id.etSubjectScore)
            val name = etSubjectName.text.toString()
            val score = etSubjectScore.text.toString()
            db.addScore(name, score)
            // Toast to message on the screen
            Toast.makeText(this, name + " added to database", Toast.LENGTH_SHORT).show()
            etSubjectName.text.clear()
            etSubjectScore.text.clear()
        }

        val btnPrintSubjects = findViewById<Button>(R.id.btnPrintSubjects)
        btnPrintSubjects.setOnClickListener {
            val db = DBHelper(this, null)
            val userList = db.getAllSubjects()
            val tvSubjectRecord = findViewById<TextView>(R.id.tvSubjectRecord)
            tvSubjectRecord.text = "### Subjects ###\n"
            userList.forEach {
                tvSubjectRecord.append("$it\n")
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
            val subjectScore = findViewById<EditText>(R.id.etSubjectScore).text.toString()
            val rows = db.updateSubject(subjectName, subjectScore)
            Toast.makeText(this, "$rows subjects updated", Toast.LENGTH_LONG).show()
        }
    }
}