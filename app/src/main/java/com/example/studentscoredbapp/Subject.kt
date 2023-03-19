package com.example.studentscoredbapp

class Subject (var id: Int, val subjectName:String, val subjectScore: String) {
    override fun toString(): String { // return the record detail
        return "$id: $subjectName: $subjectScore"
    }
}