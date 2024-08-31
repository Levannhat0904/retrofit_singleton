package net.braniumacademy.learnrecyclerview.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import net.braniumacademy.learnrecyclerview.R
import net.braniumacademy.learnrecyclerview.data.LocalDataSource
import net.braniumacademy.learnrecyclerview.data.Student
import net.braniumacademy.learnrecyclerview.ui.detail.StudentDetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerStudent: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        recyclerStudent = findViewById(R.id.rv_student)
        studentAdapter = StudentAdapter(object : StudentAdapter.OnStudentClickListener {
            override fun onClick(student: Student) {
                navigateToDetail(student)
            }
        })
        recyclerStudent.adapter = studentAdapter
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerStudent.addItemDecoration(divider)
    }

    private fun navigateToDetail(student: Student) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        intent.putExtra(StudentDetailActivity.EXTRA_STUDENT_ID, student.id)
        startActivity(intent)
    }

    // todo 6 done: sử dụng singleton object của lớp LocalDataSource để load danh sách sinh viênt từ file json
    private fun setupViewModel() {
        val dataSource = LocalDataSource.getInstance(resources)
        val viewModelFactory = StudentViewModel.Factory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[StudentViewModel::class.java]
        viewModel.loadStudents()
        viewModel.students.observe(this) {
            studentAdapter.updateStudents(it)
        }
    }
}