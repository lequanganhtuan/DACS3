package com.example.dacs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs.Data.Comment
import com.example.dacs.R

class CommentAdapter(private val comments: List<Comment>, private val currentUserId: String) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cmt = comments[position]
        holder.bind(cmt, currentUserId)
    }

    override fun getItemCount(): Int = comments.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val comment: TextView = itemView.findViewById(R.id.comment)
        private val option: TextView = itemView.findViewById(R.id.option)
        fun bind(cmt: Comment, currentUserId: String) {
            name.text = cmt.nameUser
            comment.text = cmt.comment
            if (cmt.iduser == currentUserId) {

                option.visibility = View.VISIBLE
            }
            else
            {
                option.visibility = View.GONE
            }
            option.setOnClickListener {
                val popupMenu = PopupMenu(this.itemView.context, option)
                popupMenu.menuInflater.inflate(R.menu.menu_option, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.edit -> {
                            // Xử lý khi click vào menu Chỉnh sửa
                            true
                        }
                        R.id.delete -> {
                            // Xử lý khi click vào menu Xóa
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }
}