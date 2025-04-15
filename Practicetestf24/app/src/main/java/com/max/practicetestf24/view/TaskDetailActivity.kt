package com.max.practicetestf24.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.max.practicetestf24.viewmodel.TaskViewModel
import com.max.practicetestf24.viewmodel.TaskViewModelFactory
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.max.practicetestf24.MyApplication
import com.max.practicetestf24.data.Task

class TaskDetailActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as
                MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TaskDetailContent(taskViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailContent(taskViewModel: TaskViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity
    val taskId = activity?.intent?.getIntExtra("taskId", -1) ?: -1
    var task by remember { mutableStateOf<Task?>(null) }
    LaunchedEffect(taskId) {
        task = taskViewModel.getTaskOnce(taskId) // 用 suspend 函数单次取值
    }
    task?.let {
        var title by remember { mutableStateOf(it.title) }
        var description by remember { mutableStateOf(it.description) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Task Detail") },
                    navigationIcon = {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = "Task Details")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title, onValueChange = { title = it },
                    label = { Text("Task Title") },
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = description, onValueChange = {
                    description = it
                }, label = { Text("Task Description") },
                    modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    val updatedTask = it.copy(
                        title = title, description =
                            description
                    )
                    taskViewModel.update(updatedTask)
                    activity?.finish()
                }) {
                    Text("Update Task")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    taskViewModel.delete(it)
                    activity?.finish()
                }) {
                    Text("Delete Task")
                }

                Spacer(modifier = Modifier.height(8.dp))

//                Button(onClick = {
//                    activity?.finish()
//                }) {
//                    Text("Back")
//                }
            }
        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = Color.White) {
            content()
        }
    }
}
