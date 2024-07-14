package taskmanager.example.com.ui.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import taskmanager.example.com.database.entities.TaskEntity
import taskmanager.example.com.models.TaskModel
import taskmanager.example.com.repositories.TaskRepositoryImpl

class HomeViewModel(
    private val repository: TaskRepositoryImpl
) : ViewModel() {

    var selectedItem by mutableIntStateOf(1)  // Inicialize com um valor de status apropriado

    private val _tasks = MutableLiveData<List<TaskEntity>>()
    val tasks: LiveData<List<TaskEntity>> = _tasks

    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String> = _searchValue

    init {
        getAllByStatus("1")  // Inicialize com o status padrão
    }

    fun updateSearchValue(newValue: String) {
        _searchValue.value = newValue
        getAllByStatusAndTitleOrDescription()  // Atualize a lista com o novo valor de pesquisa
    }

    fun getAllByStatus(status: String) {
        viewModelScope.launch {
            _tasks.value = repository.getAllByStatus(status)
        }
    }

    fun getAllByStatusAndTitleOrDescription() {
        viewModelScope.launch {
            _tasks.value = repository.getAllByStatusAndTitleOrDescription(
                selectedItem.toString(),
                searchValue.value.orEmpty()  // Use o valor atual da pesquisa
            )
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
            getAllByStatus(selectedItem.toString())  // Atualize a lista após a exclusão
        }
    }
}

