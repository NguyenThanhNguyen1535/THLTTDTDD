package com.ute.bai18 // Thay bằng package của bạn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.bai18.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var currentStudent: Student

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentStudent = intent.getSerializableExtra("CURRENT_STUDENT") as? Student
            ?: Student("", "", 0.0)

        // Hiển thị dữ liệu hiện tại lên form
        binding.edtName.setText(currentStudent.name)
        binding.edtMsv.setText(currentStudent.msv)
        binding.edtGpa.setText(currentStudent.gpa.toString())

        binding.btnSave.setOnClickListener {
            val newName = binding.edtName.text.toString().trim()
            val newMsv = binding.edtMsv.text.toString().trim()
            val newGpa = binding.edtGpa.text.toString().toDoubleOrNull()

            if (newName.isEmpty() || newMsv.isEmpty() || newGpa == null || newGpa !in 0.0..4.0) {
                Toast.makeText(this, R.string.msg_invalid_input, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedStudent = currentStudent.copy(name = newName, msv = newMsv, gpa = newGpa)

            val resultIntent = Intent().apply {
                putExtra("UPDATED_STUDENT", updatedStudent)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}