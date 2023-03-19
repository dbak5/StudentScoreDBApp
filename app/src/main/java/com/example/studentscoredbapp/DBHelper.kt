package com.example.studentscoredbapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "smt"
        private val DB_VERSION = 1
        val TABLE_NAME = "user_table"
        val ID = "id"
        val NAME = "name"
        val AGE = "age"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$ID INTEGER PRIMARY KEY," +
                        "$NAME TEXT," +
                        "$AGE TEXT" + ")"
                )
        db?.execSQL(query) // nullable
    }
    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // non-null assertion.         Error if null at compile time
        onCreate(db)
    }
    // This method is to add a User record in DB
    fun addUser(name: String, age: String) {
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        // insert key-value pairs
        values.put(NAME, name)
        values.put(AGE, age)
        // create a writable DB variable of our database to insert record
        val db = this.writableDatabase
        // insert all values into DB
        db.insert(TABLE_NAME, null, values)
        // close DB
        db.close()
    }
    // This method is get all User records from DB
    fun getAllUsers(): Cursor? {
        // create a readable DB variable of our database to read record
        val db = this.readableDatabase
        // read all records from DB
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }
    fun deleteUser(name: String): Int {
        // create a writable DB variable of our database to delete record
        val db = this.writableDatabase
        // delete a user by NAME
        val rows = db.delete(TABLE_NAME, "name=?", arrayOf(name))
        db.close();
        return rows // 0 or 1
    }
    fun updateUser(name: String, age: String): Int {
        // create a writable DB variable of our database to update record
        val db = this.writableDatabase
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        values.put(AGE, age)
        val rows = db.update(TABLE_NAME, values, "name=?", arrayOf(name))
        db.close()
        return rows // rows updated
    }
    // This method is to recreated DB and tables
    fun recreateDatabaseAndTables() {
    }
}