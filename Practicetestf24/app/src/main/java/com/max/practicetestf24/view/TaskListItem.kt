package com.max.practicetestf24.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import com.max.practicetestf24.data.Task
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp

@Composable
fun TaskListItem(task: Task, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(text = task.title, style =
                MaterialTheme.typography.titleLarge)
            Text(text = task.description, style =
                MaterialTheme.typography.bodyLarge)
        }
    }
}