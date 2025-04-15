package com.max.practicetestf24.repository

import com.max.practicetestf24.data.Task
import com.max.practicetestf24.data.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
    suspend fun update(task: Task) {
        taskDao.update(task)
    }
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
    suspend fun getTask(id: Int): Task? {
        return taskDao.getTask(id)
    }
    fun searchTasks(searchQuery: String): Flow<List<Task>> {
        return taskDao.searchTasks("%$searchQuery%")
    }
}