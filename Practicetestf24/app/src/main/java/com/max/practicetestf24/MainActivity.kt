package com.max.practicetestf24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.max.practicetestf24.data.Task
import com.max.practicetestf24.view.MyApp
import com.max.practicetestf24.viewmodel.TaskViewModel
import com.max.practicetestf24.viewmodel.TaskViewModelFactory
import com.max.practicetestf24.view.TaskListItem
import kotlin.getValue
import androidx.compose.material3.*

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.max.practicetestf24.view.TaskDetailActivity
import kotlinx.coroutines.flow.StateFlow


class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as
                MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp {
                MainActivityContent(taskViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(taskViewModel: TaskViewModel) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<StateFlow<List<Task>>?>(null) }
    val tasks by (searchResult ?: taskViewModel.allTasks).collectAsState()
    //    val tasks by taskViewModel.allTasks.collectAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("📝 Task Manager") }
            )
        }
    ) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(text = "Add Task")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description =
            it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val task = Task(title = title, description = description)
            taskViewModel.insert(task)
            title = ""
            description = ""
        }) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Search Tasks")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (searchQuery.isNotBlank()) {
                searchResult = taskViewModel.searchTasks(searchQuery)
            } else {
                searchResult = null
            }
        }) {
            Text("Search")
        }
        Button(onClick = {
            searchResult = null
            searchQuery = ""
        }) {
            Text("Clear Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Task List")
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(count = tasks.size) { index ->
                val task = tasks[index]
                TaskListItem(
                    task = task,
                    onClick = {
                        val intent = Intent(context, TaskDetailActivity::class.java)
                        intent.putExtra("taskId", task.id)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}
}