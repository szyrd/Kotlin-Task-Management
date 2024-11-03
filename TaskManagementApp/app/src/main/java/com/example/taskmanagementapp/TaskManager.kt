package com.example.taskmanagementapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object TaskManager {
    private val tasks = mutableListOf<Task>()
    private val _tasksLiveData = MutableLiveData<List<Task>>(tasks)
    val tasksLiveData: LiveData<List<Task>> = _tasksLiveData

    fun getTasks(): List<Task> = tasks

    fun getNextId(): Int = tasks.size + 1

    fun addTask(task: Task) {
        tasks.add(task)
        _tasksLiveData.value = tasks
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
        _tasksLiveData.value = tasks
    }

    fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
            _tasksLiveData.value = tasks
        }
    }
    fun getFilteredTasks(keyword: String): List<Task> {
        return tasks.filter { it.title.contains(keyword, ignoreCase = true) }
    }
    fun getSortedTasks(): List<Task> {
        return tasks.sortedBy { it.title }
    }

}






