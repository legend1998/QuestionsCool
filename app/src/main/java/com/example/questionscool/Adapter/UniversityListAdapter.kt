package com.example.questionscool.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.CourseListActivity
import com.example.questionscool.Model.University
import com.example.questionscool.R
import kotlinx.android.synthetic.main.item_tile.view.*

class UniversityListAdapter(val context: Context, private val univlist: ArrayList<University>) :

    RecyclerView.Adapter<UniversityListAdapter.UnivHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnivHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tile, parent, false)
        return UnivHolder(view)
    }

    override fun getItemCount(): Int {
        return univlist.size
    }

    override fun onBindViewHolder(holder: UnivHolder, position: Int) {


        val univ = univlist[position]
        holder.title.text = univ.name
        holder.subtitle.text = univ.address

        univ.name?.let { Log.d("adapter", it) }
        Log.d("falana","success");


        holder.itemView.setOnClickListener {
            val intent = Intent(context,CourseListActivity::class.java)
            intent.putExtra("univid",univ.id)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }


    }

    class UnivHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_tile)
        val subtitle: TextView = view.findViewById(R.id.subtitle_tile)



    }
}