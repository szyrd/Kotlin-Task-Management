package com.example.taskmanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TaskListener {

    private lateinit var taskManager: TaskManager
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskManager = TaskManager
        taskAdapter = TaskAdapter(taskManager.getTasks(), listener = this)

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        addTaskButton = findViewById(R.id.addTaskButton)

        taskRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        taskRecyclerView.adapter = taskAdapter

        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }

        // Observe LiveData for tasks list changes and update the adapter
        taskManager.tasksLiveData.observe(this, Observer { updatedTasks ->
            taskAdapter.updateTasks(updatedTasks)
            taskAdapter.notifyDataSetChanged()
        })
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTextTitle = dialogLayout.findViewById<EditText>(R.id.editTextTaskTitle)
        val editTextDescription = dialogLayout.findViewById<EditText>(R.id.editTextTaskDescription)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)


        builder.setTitle("Add task")
            .setView(dialogLayout)
            .setPositiveButton("Save") { _, _ ->
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty()) {
                    val newTask = Task(taskManager.getNextId(), title, description)
                    taskManager.addTask(newTask)
                    taskAdapter.notifyDataSetChanged() // Notify the adapter about the new task
                } else {
                    Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    override fun onPause() {
        super.onPause()
        // Save the current state of the app, such as task list or other essential data
    }


    override fun onTaskSelected(task: Task) {
        val intent = Intent(this, TaskDetailActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

    override fun onTaskUpdated(task: Task) {
        taskManager.updateTask(task)
        taskAdapter.notifyDataSetChanged()
    }

    override fun onTaskDeleted(task: Task) {
        taskManager.removeTask(task)
        taskAdapter.notifyDataSetChanged()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Optionally, you could add any additional logic here if needed.
    }

}






















