package taskmanager.example.com.ui.viewmodels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import taskmanager.example.com.models.TaskModel
import taskmanager.example.com.repositories.TaskRepositoryImpl

data class DataTask(
    var id: Int = 0,
    var title: String,
    var description: String,
    var status: String,
    var time: String,
    var data: String
)

class CreateViewModel(private val repository: TaskRepositoryImpl) : ViewModel() {

    var dataTask by mutableStateOf(DataTask(
        id = 0,
        title = "",
        description = "",
        status = "1",
        time = "",
        data = ""
    ))

    private val _selectedStatus = mutableStateOf(
        listOf(false, false, false)
    )

    val selectedStatus: MutableState<List<Boolean>> = _selectedStatus

    fun changeSelectedStatus(status: List<Boolean>) {
        _selectedStatus.value = status
    }

    fun clear() {
        dataTask = DataTask(
            id = 0,
            title = "",
            description = "",
            status = "1",
            time = "",
            data = ""
        )

        changeSelectedStatus(listOf(false, false, false))
    }



    fun insert(
        title: String,
        description: String,
        data: String,
        time: String,
        status: String
    ) {
        viewModelScope.launch {
            repository.insert(
                TaskModel(
                    title = title,
                    description = description,
                    data = data,
                    time = time,
                    status = status
                )
            )

        }
    }

}
