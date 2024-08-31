package net.braniumacademy.learnrecyclerview.data

import android.content.res.Resources
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.braniumacademy.learnrecyclerview.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringWriter
import java.nio.charset.StandardCharsets

// todo 1 done: triển khai mẫu singleton cho lớp này
class LocalDataSource private constructor(
    private val resources: Resources
) {
    // todo 2 done: bổ sung thuộc tính lưu lại danh sách sinh viên sau mỗi lần đọc file
    private val students = mutableListOf<Student>()

    fun loadStudents(): List<Student> {
        val jsonString = getJsonFromFile()
        val mapper = jacksonObjectMapper()
        try {
            val result = mapper.readValue<List<Student>>(jsonString)
            students.clear()
            students.addAll(result)
        } catch (_: JsonParseException) {
            emptyList<Student>()
        }
        return students
    }

    private fun getJsonFromFile(): String {
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            resources.openRawResource(R.raw.student).use { inputStream ->
                val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                var size: Int
                while (reader.read(buffer).also { size = it } != -1) {
                    writer.write(buffer, 0, size)
                }
            }
        } catch (_: IOException) {
        }
        return writer.toString()
    }

    fun findStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    // todo 3 done: công bố đối tượng singleton của lớp này ra bên ngoài qua khối companion object
    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(resources: Resources): LocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSource(resources).also { instance = it }
            }
        }
    }
}