package com.example.questionscool.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Model.Course
import com.example.questionscool.PaperQActivity
import com.example.questionscool.R
import kotlinx.android.synthetic.main.item_tile.view.*

class CourseAdapter(val context: Context, val courseList: ArrayList<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tile, parent, false)
        return CourseHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        val course: Course = courseList[position]
        holder.title.text = course.name
        holder.subtitle.text = course.code

        holder.itemView.setOnClickListener {
            val intent = Intent(context,PaperQActivity::class.java)
            intent.putExtra("courseid",course.id)
            intent.putExtra("univid",course.university_id)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }


    class CourseHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title: TextView = view.findViewById(R.id.title_tile)
        var subtitle: TextView = view.findViewById(R.id.subtitle_tile)



    }
}