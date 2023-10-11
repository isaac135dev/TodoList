package com.example.todolist.ViewModels

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Models.ToDoListItem
import com.example.todolist.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoLitsAdapter : RecyclerView.Adapter<ToDoLitsAdapter.ViewHolder>() {

    private var items = mutableListOf<ToDoListItem>()

    companion object {
        private const val DELETE_THRESHOLD = 100
    }

    fun setItems(items: List<ToDoListItem>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): ToDoListItem {
        return items[position]
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun findItemPositionById(itemId: String): Int {
        return items.indexOfFirst { it.id == itemId }
    }

    fun deleteItemPermanently(viewHolder: ViewHolder) {
        val position = viewHolder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val itemToDelete = items[position]

            items.removeAt(position)
            notifyItemRemoved(position)

            viewHolder.itemToDelete = null
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoLitsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ToDoLitsAdapter.ViewHolder, position: Int) {
        val item = items[position]

        val dateFormat = SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.getDefault())

        val dueDate = item.dueDate
        val date = Date(dueDate * 1000)
        val dueDateFormatted = dateFormat.format(date)


        holder.titleView.text = item.title
        holder.subtitleItem.text = dueDateFormatted

//        holder.itemView.setOnTouchListener { _, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    holder.startX = event.x // Almacenar la posición de inicio
//                    holder.itemToDelete = item // Almacenar el elemento a eliminar
//                }
//                MotionEvent.ACTION_UP -> {
//                    if (holder.startX != 0f && event.x - holder.startX < -DELETE_THRESHOLD) {
//                        deleteItemPermanently(holder)
//                    }
//                    holder.startX = 0f // Restablecer la posición de inicio
//                }
//                MotionEvent.ACTION_CANCEL -> {
//                    holder.startX = 0f // Restablecer la posición de inicio
//                }
//            }
//            false
//        }
//        holder.itemView.setOnTouchListener { _, event ->
//            val action = event.actionMasked
//            val swipeThreshold = holder.deleteTextView.width / 2
//
//            when (action) {
//                MotionEvent.ACTION_DOWN -> {
//                    holder.startX = event.x
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    val deltaX = event.x - holder.startX
//
//                    holder.deleteBackground.visibility = if (deltaX < -swipeThreshold) View.VISIBLE else View.INVISIBLE
//                    holder.deleteTextView.visibility = if (deltaX < -swipeThreshold) View.VISIBLE else View.INVISIBLE
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    if (event.x - holder.startX < -swipeThreshold){
//                        deleteItemPermanently()
//                    }
//                }
//            }
//            true
//        }
    }

    override fun getItemCount() = items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.tvTitleItem)
        val subtitleItem: TextView = itemView.findViewById(R.id.tvSubtitleItem)
        val deleteTextView: TextView = itemView.findViewById(R.id.deleteTextView)
        val deleteBackground: View = itemView.findViewById(R.id.deleteBackground)
        var startX: Float = 0f
        var itemToDelete: ToDoListItem? = null
    }
}