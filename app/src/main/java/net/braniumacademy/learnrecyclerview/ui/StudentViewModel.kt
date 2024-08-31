package net.braniumacademy.learnrecyclerview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.braniumacademy.learnrecyclerview.data.LocalDataSource
import net.braniumacademy.learnrecyclerview.data.Student

class StudentViewModel(
    private val dataSource: LocalDataSource
) : ViewModel() {
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    fun loadStudents() {
        val studentList = dataSource.loadStudents()
        _students.value = studentList
    }

    class Factory(
        private val dataSource: LocalDataSource
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StudentViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}