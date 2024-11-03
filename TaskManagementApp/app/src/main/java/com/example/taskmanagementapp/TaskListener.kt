package com.example.taskmanagementapp

interface TaskListener {
    fun onTaskSelected(task: Task)
    fun onTaskUpdated(task: Task)
    fun onTaskDeleted(task: Task)
}



