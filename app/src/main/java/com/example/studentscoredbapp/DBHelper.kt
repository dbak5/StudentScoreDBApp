package com.example.studentscoredbapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "smt"
        private val DB_VERSION = 1
        val TABLE_NAME = "subject_table"
        val ID = "id"
        val NAME = "name"
        val SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$ID INTEGER PRIMARY KEY," +
                        "$NAME TEXT," +
                        "$SCORE TEXT" + ")"
                )
        db?.execSQL(query) // nullable
    }

    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // non-null assertion.         Error if null at compile time
        onCreate(db)
    }

    // This method is to add a subject record in DB
    fun addScore(name: String, score: String) {
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        // insert key-value pairs
        values.put(NAME, name)
        values.put(SCORE, score)
        // create a writable DB variable of our database to insert record
        val db = this.writableDatabase
        // insert all values into DB
        db.insert(TABLE_NAME, null, values)
        // close DB
        db.close()
    }

    // This method is get all subject records from DB
    fun getAllSubjects(): ArrayList<Subject> {
        val db = this.readableDatabase
        // read all records from DB and get the cursor
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        val subjectList = ArrayList<Subject>() // User ArrayList
        if (cursor.moveToFirst()) {
            do { // add all users to the list
                subjectList.add(
                    Subject(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SCORE))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return subjectList
    }

    fun deleteSubject(name: String): Int {
        // create a writable DB variable of our database to delete record
        val db = this.writableDatabase
        // delete a subject by NAME
        val rows = db.delete(TABLE_NAME, "name=?", arrayOf(name))
        db.close();
        return rows // 0 or 1
    }

    fun updateSubject(name: String, score: String): Int {
        // create a writable DB variable of our database to update record
        val db = this.writableDatabase
        // This ContentValues class is used to store a set of values
        val values = ContentValues()
        values.put(SCORE, score)
        val rows = db.update(TABLE_NAME, values, "name=?", arrayOf(name))
        db.close()
        return rows // rows updated
    }

    // This method is to recreated DB and tables
    fun recreateDatabaseAndTables() {
    }
}