package com.example.studentscoredbapp

class Subject (private var id: Int, private val subjectName:String, private val subjectScore: String) {
    override fun toString(): String { // return the record detail
        return "$id: $subjectName: $subjectScore"
    }
}