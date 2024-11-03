package com.example.taskmanagementapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(var id: Int, var title: String, var description: String) : Parcelable





