package com.ute.bai18 // Thay bằng package của bạn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.bai18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentStudent: Student
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo thông tin sinh viên mặc định theo yêu cầu của bạn
        currentStudent = Student("Nguyễn Thành Nguyên", "2415053122331", 3.6)
        bindStudentData(currentStudent)

        editProfileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
                updatedStudent?.let {
                    currentStudent = it
                    bindStudentData(currentStudent)
                    Toast.makeText(this, R.string.msg_saved_success, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("CURRENT_STUDENT", currentStudent)
            }
            editProfileLauncher.launch(intent)
        }
    }

    private fun bindStudentData(student: Student) {
        binding.tvName.text = getString(R.string.label_name, student.name)
        binding.tvMsv.text = getString(R.string.label_msv, student.msv)
        binding.tvGpa.text = getString(R.string.label_gpa, student.gpa)
    }
}