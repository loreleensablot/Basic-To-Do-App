package com.sablot.basictodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sablot.basictodo.databinding.ItemTodoBinding

class TodoAdapter(
    var todoList: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = todoList[position].title
            cbToDo.apply {
                isChecked = todoList[position].isChecked
                setOnClickListener {
                    todoList[position].isChecked = !todoList[position].isChecked
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return todoList.size
    }

}