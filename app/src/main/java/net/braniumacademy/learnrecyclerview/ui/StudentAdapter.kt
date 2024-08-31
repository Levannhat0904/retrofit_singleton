package net.braniumacademy.learnrecyclerview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.braniumacademy.learnrecyclerview.R
import net.braniumacademy.learnrecyclerview.data.Student
import net.braniumacademy.learnrecyclerview.utils.Utils

class StudentAdapter(
    private val listener: OnStudentClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private val students = mutableListOf<Student>()

    class StudentViewHolder(
        itemView: View,
        private val listener: OnStudentClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageAvatar: ImageView = itemView.findViewById(R.id.image_avatar)
        private val textId: TextView = itemView.findViewById(R.id.text_student_id)
        private val textFullName: TextView = itemView.findViewById(R.id.text_full_name)
        private val textGpa: TextView = itemView.findViewById(R.id.text_gpa)

        fun bind(student: Student) {
            textId.text = student.id
            textFullName.text = student.fullName.toString()
            textGpa.text = student.gpa.toString()
            imageAvatar.setImageResource(Utils.getAvatar(student.gender))
            itemView.setOnClickListener {
                listener.onClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(rootView, listener)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    fun updateStudents(studentList: List<Student>) {
        val oldSize = students.size
        students.clear()
        students.addAll(studentList)
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, students.size)
    }

    interface OnStudentClickListener {
        fun onClick(student: Student)
    }
}