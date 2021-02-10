package com.example.questionscool.Adapter

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Model.Paper
import com.example.questionscool.R
import java.io.File
import java.lang.Exception

class DownloadAdapter(
    val context: Context,
    val paperlist: ArrayList<Paper>,
    val activity: Activity
) :
    RecyclerView.Adapter<DownloadAdapter.DownloadHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.download_tiles, parent, false)
        return DownloadHolder(view)

    }

    override fun getItemCount(): Int {
        return paperlist.size
    }

    override fun onBindViewHolder(holder: DownloadHolder, position: Int) {
        val p: Paper = paperlist[position]
        holder.title.text = p.yearandsam

        holder.itemView.setOnClickListener {

            try {
                val uri: Uri = Uri.fromFile(File(p.link))
                val mime: String = "application/pdf"

                val intent = Intent()
                intent.setAction(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, mime)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "there is some error in file uri", Toast.LENGTH_LONG).show()
                Log.d("debug",e.toString())
            }
        }

    }


    class DownloadHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title_download_pdf)
    }
}