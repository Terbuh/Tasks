package popowicz.hubert.tasks.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import popowicz.hubert.tasks.api.TaskNetworkRepository
import popowicz.hubert.tasks.database.TaskDatabaseRepository
import popowicz.hubert.tasks.model.Task
import popowicz.hubert.tasks.model.TaskIdResponse
import popowicz.hubert.tasks.model.TaskOperationStatus

class TaskViewModel(
    private val taskDatabaseRepository: TaskDatabaseRepository,
    private val taskNetworkRepository: TaskNetworkRepository
) : ViewModel() {

    var taskList by mutableStateOf(emptyList<Task>())
    var addEditTaskStatus by mutableStateOf(TaskOperationStatus.UNKNOWN)
    var getAllTasksStatus by mutableStateOf(TaskOperationStatus.UNKNOWN)
    var sendSmsTaskStatus by mutableStateOf<Task?>(null)

    fun getAllTasks() {
        viewModelScope.launch {
            try {
                getAllTasksStatus = TaskOperationStatus.LOADING
                taskList = taskNetworkRepository.getAllTasks().toMutableList()
                taskDatabaseRepository.insertAllTask(taskList)
                getAllTasksStatus = TaskOperationStatus.SUCCESS
            } catch (e: Exception) {
                taskList = taskDatabaseRepository.getAllTasks()
                getAllTasksStatus = TaskOperationStatus.ERROR
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                addEditTaskStatus = TaskOperationStatus.LOADING
                val response: TaskIdResponse = taskNetworkRepository.addTask(task)
                taskDatabaseRepository.insertTask(task.copy(id = response.name))
                addEditTaskStatus = TaskOperationStatus.SUCCESS
            } catch (e: Exception) {
                addEditTaskStatus = TaskOperationStatus.ERROR
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskNetworkRepository.deleteTask(task.id)
                taskDatabaseRepository.deleteTask(task)
                removeTaskFromList(task)
            } catch (e: Exception) {

            }
        }
    }

    private fun removeTaskFromList(task: Task) {

        taskList.toMutableList().also {
            it.remove(task)
            taskList = it
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            try {
                addEditTaskStatus = TaskOperationStatus.LOADING
                taskNetworkRepository.editTask(task)
                taskDatabaseRepository.editTask(task)
                addEditTaskStatus = TaskOperationStatus.SUCCESS
            } catch (e: Exception) {
                addEditTaskStatus = TaskOperationStatus.ERROR
            }
        }
    }

}