package pl.edu.ansnt.todo.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.edu.ansnt.todo.model.Task
import pl.edu.ansnt.todo.network.TasksAPI

class TasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            val fetchedTasks = TasksAPI.service.getTasks()
            _tasks.value = fetchedTasks
        }
    }



    fun toggleTaskDone(task: Task) {
        val updatedTask = task.copy(done = !task.done)
        _tasks.value = _tasks.value.map { if (it == task) updatedTask else it }

        viewModelScope.launch {
            try {
                TasksAPI.service.updateTask(updatedTask.id, updatedTask)
            } catch (e: Exception) {
                _tasks.value = _tasks.value.map { if (it == updatedTask) task else it }

                Log.e("TaskUpdateError", "Failed to update task: ${e.message}")
            }
        }
    }
}
