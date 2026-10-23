package com.ute.bai18

import java.io.Serializable

data class Student(
    val name: String,
    val msv: String,
    val gpa: Double
) : Serializable