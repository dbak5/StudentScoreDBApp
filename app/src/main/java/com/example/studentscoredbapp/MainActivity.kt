package com.example.studentscoredbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddUser = findViewById<Button>(R.id.btnAddUser)
        btnAddUser.setOnClickListener {
            val db = DBHelper(this, null)
            val etUserName = findViewById<EditText>(R.id.etUserName)
            val etAge = findViewById<EditText>(R.id.etAge)
            val name = etUserName.text.toString()
            val age = etAge.text.toString()
            db.addUser(name, age)
            // Toast to message on the screen
            Toast.makeText(this, name + " added to database", Toast.LENGTH_SHORT).show()
            etUserName.text.clear()
            etAge.text.clear()
        }

        val btnPrintUsers = findViewById<Button>(R.id.btnPrintUsers)
        btnPrintUsers.setOnClickListener {
            val db = DBHelper(this, null)
            val cursor = db.getAllUsers()
            cursor!!.moveToFirst()
            val tvUserRecord = findViewById<TextView>(R.id.tvUserRecord)
            tvUserRecord.text = "### Users ###\n"
            if (cursor!!.moveToFirst()) {
                tvUserRecord.append(cursor.getString(0) + ": " +
                        cursor.getString(1) +
                        "(" + cursor.getString(2) + ")\n")
            }
            while (cursor.moveToNext()) {
                tvUserRecord.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)) +
                        ": " + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME)) +
                        "(" + cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AGE)) + ")\n")
            }
        }

        val btnDeleteUser = findViewById<Button>(R.id.btnDeleteUser)
        btnDeleteUser.setOnClickListener {
            val db = DBHelper(this, null)
            val userName = findViewById<EditText>(R.id.etUserName).text.toString()
            val rows = db.deleteUser(userName)
            Toast.makeText(this,
                when (rows) {
                    0 -> "Nothing deleted"
                    1 -> "1 user deleted"
                    else -> "" // shouldn't happen
                },
                Toast.LENGTH_LONG).show()
        }

        val btnUpdateUser = findViewById<Button>(R.id.btnUpdateUser)
        btnUpdateUser.setOnClickListener {
            val db = DBHelper(this, null)
            val userName = findViewById<EditText>(R.id.etUserName).text.toString()
            val age = findViewById<EditText>(R.id.etAge).text.toString()
            val rows = db.updateUser(userName, age)
            Toast.makeText(this, "$rows users updated", Toast.LENGTH_LONG).show()
        }
    }
}