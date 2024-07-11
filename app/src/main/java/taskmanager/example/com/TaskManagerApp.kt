package taskmanager.example.com

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import taskmanager.example.com.ui.components.Bottombar
import taskmanager.example.com.ui.components.Topbar
import taskmanager.example.com.ui.components.TopbarButton
import taskmanager.example.com.ui.screens.CreateTaskScreen
import taskmanager.example.com.ui.screens.HomeScreen
import taskmanager.example.com.ui.screens.UpdateTaskScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerApp() {
    val navController = rememberNavController()

     NavHost(navController = navController, startDestination = "home") {

           composable("home") {
                HomeScreen(
                    navController
                )
           }

            composable("create") {
                CreateTaskScreen(
                    navController
                )
            }

            composable("update/{taskId}") { backstackEntry ->
                backstackEntry.arguments?.getString("taskId")
                    ?.let { taskId ->
                        UpdateTaskScreen(
                            taskId.toInt(),
                            navController
                        )
                    }

            }

            composable("delete/{taskId}") { backstackEntry ->
                backstackEntry.arguments?.getString("taskId")?.toInt()
            }
     }

}

