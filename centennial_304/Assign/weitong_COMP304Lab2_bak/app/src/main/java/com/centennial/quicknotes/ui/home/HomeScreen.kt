// ui/home/HomeScreen.kt
package com.centennial.quicknotes.ui.home
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.centennial.quicknotes.data.Note
import com.centennial.quicknotes.ui.add.AddNoteScreen
import com.centennial.quicknotes.ui.detail.NoteDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val notes = remember { mutableStateListOf(
        Note("Note One", "I am note one! I am happy!"),
        Note("Heal the World", "Think about on the generations\n" +
                "And say we would make it a better place\n" +
                "For children and children's children."),
        Note("Note Three", "Cardio on Monday, Strength training on Wednesday.")
    )}

    // 使用 Scaffold 包裹整个界面
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addNote") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        // 将 NavHost 放在 Scaffold 的内容区
        NavHost(
            navController = navController,
            startDestination = "notes",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("notes") { NotesScreen(navController, notes) }
            composable("noteDetail/{title}/{content}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: "No Title"
                val content = backStackEntry.arguments?.getString("content") ?: "No Content"
                // 找到当前笔记的索引
                val noteIndex = notes.indexOfFirst { it.title == title && it.content == content }
                NoteDetailScreen(
                    initialTitle = title,
                    initialContent = content,
                    navController = navController,
                    onSave = { newTitle, newContent ->
                        // 更新笔记
                        if (noteIndex != -1) {
                            notes[noteIndex] = Note(newTitle, newContent)
                        }
                    }
                )
            }
            composable("addNote") { AddNoteScreen(navController, notes) }
        }
    }
}