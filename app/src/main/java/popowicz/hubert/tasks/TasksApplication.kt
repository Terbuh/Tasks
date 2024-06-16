package popowicz.hubert.tasks

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import popowicz.hubert.tasks.api.ServiceConfiguration
import popowicz.hubert.tasks.api.TaskNetworkRepository
import popowicz.hubert.tasks.database.DatabaseConfiguration
import popowicz.hubert.tasks.database.TaskDatabaseRepository
import popowicz.hubert.tasks.viewmodel.TaskViewModel

class TasksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTasksApp", "AppOnCreate()")

        startKoin {
            androidContext(this@TasksApplication)
            modules(
                module {
                    single { DatabaseConfiguration.getDatabase(androidContext()) }
                    factory { TaskDatabaseRepository(get()) }

                    single { ServiceConfiguration.taskService }
                    factory { TaskNetworkRepository(get()) }

                    viewModel { TaskViewModel(get(), get()) }
                }
            )
        }
    }
}