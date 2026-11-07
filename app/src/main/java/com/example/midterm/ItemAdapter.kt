package com.example.midterm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: MutableList<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
        val removeButton: Button = view.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        val holder = ViewHolder(view)
        holder.removeButton.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                removeItem(position)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    /** Adds the item and returns true, or returns false if it's already on the list. */
    fun addItem(item: String): Boolean {
        if (items.any { it.equals(item, ignoreCase = true) }) {
            return false
        }
        items.add(item)
        notifyItemInserted(items.size - 1)
        return true
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
