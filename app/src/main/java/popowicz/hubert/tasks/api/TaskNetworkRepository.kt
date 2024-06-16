package popowicz.hubert.tasks.api

import popowicz.hubert.tasks.model.Task
import popowicz.hubert.tasks.model.TaskIdResponse

class TaskNetworkRepository(private val taskService: TaskService) {
    suspend fun addTask(task: Task): TaskIdResponse {
        return taskService.add(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskService.getAll().map { element -> element.value.copy(id = element.key) }
    }

    suspend fun deleteTask(taskId: String) {
        taskService.deleteTask(taskId)
    }

    suspend fun editTask(task: Task) {
        taskService.editTask(task.id, task)
    }
}