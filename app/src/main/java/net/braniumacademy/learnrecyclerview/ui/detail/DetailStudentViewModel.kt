package net.braniumacademy.learnrecyclerview.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.braniumacademy.learnrecyclerview.data.LocalDataSource
import net.braniumacademy.learnrecyclerview.data.Student

// todo 4: tự triển khai lớp viewmodel này sao cho phù hợp với yêu cầu sử dụng
class DetailStudentViewModel(
    private val dataSource: LocalDataSource
) : ViewModel() {
    private val _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    fun findStudentById(studentId: String) {
        _student.value = dataSource.findStudentById(studentId)
    }

    class Factory(
        private val dataSource: LocalDataSource
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailStudentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailStudentViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}