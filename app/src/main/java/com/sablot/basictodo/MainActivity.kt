package com.sablot.basictodo

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sablot.basictodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
    }

    private fun sampleTodoList(): MutableList<Todo> {
        return mutableListOf(
            Todo("Buy milk", false),
            Todo("Buy eggs", true),
            Todo("Buy bread", false),
            Todo("Buy cheese", true),
            Todo("Buy butter", false),
            Todo("Buy yogurt", true),
            Todo("Buy meat", false),
            Todo("Buy fish", true)
        )
    }

    private fun setupRecyclerView() {
        val todoAdapter = TodoAdapter(sampleTodoList())
        binding.rvTodoList.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        setupButtonAdd(todoAdapter)
        setupSwipeLeftToDelete(todoAdapter)
    }

    private fun setupButtonAdd(todoAdapter: TodoAdapter) {
        binding.btnAdd.setOnClickListener {
            val todoTitle = binding.etAddToDo.text.toString()
            if (todoTitle.isNotEmpty()) {
                todoAdapter.todoList.add(Todo(todoTitle, false))
                todoAdapter.notifyItemInserted(todoAdapter.todoList.size - 1)
                binding.etAddToDo.text?.clear()
            }
        }
    }

    private fun setupSwipeLeftToDelete(todoAdapter: TodoAdapter) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedItem = todoAdapter.todoList.removeAt(position)
                todoAdapter.notifyItemRemoved(position)

                Snackbar.make(
                    binding.root, "An item is removed", Snackbar.LENGTH_LONG
                )
                    .setAction("Undo") {
                        // Add the item back to the original data source
                        todoAdapter.todoList.add(position, removedItem)
                        todoAdapter.notifyItemInserted(position)
                    }
                    .show()
            }
        }).attachToRecyclerView(binding.rvTodoList)
    }
}