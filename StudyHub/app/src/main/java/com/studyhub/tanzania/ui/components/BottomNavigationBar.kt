package com.studyhub.tanzania.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Bottom navigation bar for Study Hub app
 */
@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    onNavigateToSubjects: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToPastPapers: () -> Unit,
    onNavigateToProgress: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = onNavigateToHome
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Book, contentDescription = "Subjects") },
            label = { Text("Subjects") },
            selected = currentRoute == "subjects",
            onClick = onNavigateToSubjects
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Quiz, contentDescription = "Quiz") },
            label = { Text("Quiz") },
            selected = currentRoute == "quiz",
            onClick = onNavigateToQuiz
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Assignment, contentDescription = "Past Papers") },
            label = { Text("Papers") },
            selected = currentRoute == "pastpapers",
            onClick = onNavigateToPastPapers
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.TrendingUp, contentDescription = "Progress") },
            label = { Text("Progress") },
            selected = currentRoute == "progress",
            onClick = onNavigateToProgress
        )
    }
}