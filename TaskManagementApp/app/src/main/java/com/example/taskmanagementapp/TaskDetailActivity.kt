package com.example.taskmanagementapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        task = intent.getParcelableExtra("task") ?: Task(0, "No Task", "No Description")

        val titleTextView = findViewById<EditText>(R.id.taskDetailTitle)
        val descriptionTextView = findViewById<EditText>(R.id.taskDetailDescription)
        val editButton = findViewById<Button>(R.id.editTaskButton)
        val deleteButton = findViewById<Button>(R.id.deleteTaskButton)
        val backToListButton = findViewById<Button>(R.id.backToListButton)

        titleTextView.setText(task.title)
        descriptionTextView.setText(task.description)

        editButton.setOnClickListener {
            showEditTaskDialog()
        }

        deleteButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes") { _, _ ->
                    TaskManager.removeTask(task)
                    setResult(RESULT_OK) // Notify MainActivity of deletion
                    finish() // Close the activity
                }
                .setNegativeButton("No", null)
                .show()
        }
        backToListButton.setOnClickListener {
            finish()  // Close the activity and return to the previous one
        }
    }

    private fun showEditTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTextTitle = dialogLayout.findViewById<EditText>(R.id.editTextTaskTitle)
        val editTextDescription = dialogLayout.findViewById<EditText>(R.id.editTextTaskDescription)

        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)

        builder.setTitle("Edit Task")
            .setView(dialogLayout)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = editTextTitle.text.toString()
                val newDescription = editTextDescription.text.toString()
                if (newTitle.isNotEmpty()) {
                    task.title = newTitle
                    task.description = newDescription
                    TaskManager.updateTask(task)
                    setResult(RESULT_OK)
                    finish()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}















