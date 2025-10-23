package com.studyhub.tanzania.ui.screens.subjects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.ui.components.BottomNavigationBar
import com.studyhub.tanzania.ui.components.SubjectCard

/**
 * Subjects screen for Study Hub app
 */
@Composable
fun SubjectsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToNotes: (String) -> Unit
) {
    var selectedLevel by remember { mutableStateOf(EducationLevel.O_LEVEL) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Subjects",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
        
        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Level Selection
            item {
                Text(
                    text = "Select Level",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listOf(EducationLevel.O_LEVEL, EducationLevel.A_LEVEL)) { level ->
                        FilterChip(
                            onClick = { selectedLevel = level },
                            label = {
                                Text(
                                    text = if (level == EducationLevel.O_LEVEL) "O-Level" else "A-Level",
                                    fontSize = 14.sp
                                )
                            },
                            selected = selectedLevel == level,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // Subjects List
            item {
                Text(
                    text = if (selectedLevel == EducationLevel.O_LEVEL) "O-Level Subjects" else "A-Level Subjects",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getSubjectsForLevel(selectedLevel)) { subject ->
                        SubjectCard(
                            title = subject.title,
                            subtitle = subject.subtitle,
                            icon = subject.icon,
                            color = subject.color,
                            onClick = { onNavigateToNotes(subject.id) }
                        )
                    }
                }
            }
            
            // Core Subjects
            item {
                Text(
                    text = "Core Subjects",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getCoreSubjectsForLevel(selectedLevel)) { subject ->
                        SubjectCard(
                            title = subject.title,
                            subtitle = subject.subtitle,
                            icon = subject.icon,
                            color = subject.color,
                            onClick = { onNavigateToNotes(subject.id) }
                        )
                    }
                }
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "subjects",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

data class SubjectItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

fun getSubjectsForLevel(level: EducationLevel): List<SubjectItem> {
    return if (level == EducationLevel.O_LEVEL) {
        listOf(
            SubjectItem(
                id = "mathematics",
                title = "Mathematics",
                subtitle = "O-Level",
                icon = Icons.Default.Calculate,
                color = Color(0xFF2196F3)
            ),
            SubjectItem(
                id = "physics",
                title = "Physics",
                subtitle = "O-Level",
                icon = Icons.Default.Science,
                color = Color(0xFF4CAF50)
            ),
            SubjectItem(
                id = "chemistry",
                title = "Chemistry",
                subtitle = "O-Level",
                icon = Icons.Default.Science,
                color = Color(0xFFFF9800)
            ),
            SubjectItem(
                id = "biology",
                title = "Biology",
                subtitle = "O-Level",
                icon = Icons.Default.Science,
                color = Color(0xFF9C27B0)
            ),
            SubjectItem(
                id = "english",
                title = "English",
                subtitle = "O-Level",
                icon = Icons.Default.Book,
                color = Color(0xFFF44336)
            ),
            SubjectItem(
                id = "kiswahili",
                title = "Kiswahili",
                subtitle = "O-Level",
                icon = Icons.Default.Book,
                color = Color(0xFF009688)
            )
        )
    } else {
        listOf(
            SubjectItem(
                id = "mathematics_alevel",
                title = "Mathematics",
                subtitle = "A-Level",
                icon = Icons.Default.Calculate,
                color = Color(0xFF2196F3)
            ),
            SubjectItem(
                id = "physics_alevel",
                title = "Physics",
                subtitle = "A-Level",
                icon = Icons.Default.Science,
                color = Color(0xFF4CAF50)
            ),
            SubjectItem(
                id = "chemistry_alevel",
                title = "Chemistry",
                subtitle = "A-Level",
                icon = Icons.Default.Science,
                color = Color(0xFFFF9800)
            ),
            SubjectItem(
                id = "biology_alevel",
                title = "Biology",
                subtitle = "A-Level",
                icon = Icons.Default.Science,
                color = Color(0xFF9C27B0)
            ),
            SubjectItem(
                id = "economics",
                title = "Economics",
                subtitle = "A-Level",
                icon = Icons.Default.TrendingUp,
                color = Color(0xFF607D8B)
            ),
            SubjectItem(
                id = "accountancy",
                title = "Accountancy",
                subtitle = "A-Level",
                icon = Icons.Default.AccountBalance,
                color = Color(0xFF795548)
            )
        )
    }
}

fun getCoreSubjectsForLevel(level: EducationLevel): List<SubjectItem> {
    return if (level == EducationLevel.O_LEVEL) {
        listOf(
            SubjectItem(
                id = "mathematics",
                title = "Mathematics",
                subtitle = "Core",
                icon = Icons.Default.Calculate,
                color = Color(0xFF2196F3)
            ),
            SubjectItem(
                id = "english",
                title = "English",
                subtitle = "Core",
                icon = Icons.Default.Book,
                color = Color(0xFFF44336)
            ),
            SubjectItem(
                id = "kiswahili",
                title = "Kiswahili",
                subtitle = "Core",
                icon = Icons.Default.Book,
                color = Color(0xFF009688)
            )
        )
    } else {
        listOf(
            SubjectItem(
                id = "general_studies",
                title = "General Studies",
                subtitle = "Core",
                icon = Icons.Default.School,
                color = Color(0xFF607D8B)
            )
        )
    }
}