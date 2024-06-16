package popowicz.hubert.tasks.database

import popowicz.hubert.tasks.model.Task

class TaskDatabaseRepository(private val db: AppDatabase) {

    suspend fun insertTask(task: Task) {
        db.taskDao().insert(task)
    }

    suspend fun insertAllTask(taskList: List<Task>) {
        db.taskDao().insertAll(taskList)
    }

    suspend fun getAllTasks(): List<Task> {
        return db.taskDao().getAll()
    }

    suspend fun deleteTask(task: Task) {
        db.taskDao().delete(task)
    }

    suspend fun editTask(task: Task) {
        db.taskDao().edit(task)
    }

}