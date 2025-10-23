package com.studyhub.tanzania.ui.screens.quiz

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyhub.tanzania.ui.components.BottomNavigationBar

/**
 * Quiz screen for Study Hub app
 */
@Composable
fun QuizScreen(
    onNavigateBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Quizzes & Mock Tests",
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
            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "Practice", "Mock Tests", "Bookmarked")) { filter ->
                        FilterChip(
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            selected = selectedFilter == filter,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // Quick Actions
            item {
                Text(
                    text = "Quick Actions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getQuickActions()) { action ->
                        QuickActionCard(
                            title = action.title,
                            icon = action.icon,
                            color = action.color,
                            onClick = action.onClick
                        )
                    }
                }
            }
            
            // Recent Quizzes
            item {
                Text(
                    text = "Recent Quizzes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(getSampleQuizzes()) { quiz ->
                QuizCard(
                    title = quiz.title,
                    subject = quiz.subject,
                    difficulty = quiz.difficulty,
                    questionCount = quiz.questionCount,
                    timeLimit = quiz.timeLimit,
                    averageScore = quiz.averageScore,
                    isBookmarked = quiz.isBookmarked,
                    onClick = { /* TODO: Navigate to quiz */ }
                )
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "quiz",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(120.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = color,
                maxLines = 2
            )
        }
    }
}

@Composable
fun QuizCard(
    title: String,
    subject: String,
    difficulty: String,
    questionCount: Int,
    timeLimit: Int,
    averageScore: Float,
    isBookmarked: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = subject,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DifficultyChip(difficulty = difficulty)
                        QuestionCountChip(count = questionCount)
                        TimeLimitChip(timeLimit = timeLimit)
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Average Score: ${String.format("%.1f", averageScore)}%",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                IconButton(
                    onClick = { /* TODO: Toggle bookmark */ }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionCountChip(count: Int) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "$count questions",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun TimeLimitChip(timeLimit: Int) {
    Surface(
        color = Color(0xFFFF9800).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = if (timeLimit > 0) "${timeLimit}min" else "No limit",
            fontSize = 12.sp,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

data class QuickAction(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

data class QuizItem(
    val id: String,
    val title: String,
    val subject: String,
    val difficulty: String,
    val questionCount: Int,
    val timeLimit: Int,
    val averageScore: Float,
    val isBookmarked: Boolean
)

fun getQuickActions(): List<QuickAction> {
    return listOf(
        QuickAction(
            title = "Quick Quiz",
            icon = Icons.Default.Quiz,
            color = Color(0xFF4CAF50),
            onClick = { }
        ),
        QuickAction(
            title = "Mock Test",
            icon = Icons.Default.Assignment,
            color = Color(0xFF2196F3),
            onClick = { }
        ),
        QuickAction(
            title = "Practice",
            icon = Icons.Default.School,
            color = Color(0xFFFF9800),
            onClick = { }
        ),
        QuickAction(
            title = "Bookmarked",
            icon = Icons.Default.Bookmark,
            color = Color(0xFF9C27B0),
            onClick = { }
        )
    )
}

fun getSampleQuizzes(): List<QuizItem> {
    return listOf(
        QuizItem(
            id = "1",
            title = "Mathematics Practice Quiz",
            subject = "Mathematics",
            difficulty = "Intermediate",
            questionCount = 20,
            timeLimit = 30,
            averageScore = 75.5f,
            isBookmarked = true
        ),
        QuizItem(
            id = "2",
            title = "Physics Mock Test",
            subject = "Physics",
            difficulty = "Advanced",
            questionCount = 50,
            timeLimit = 120,
            averageScore = 68.2f,
            isBookmarked = false
        ),
        QuizItem(
            id = "3",
            title = "Chemistry Quick Quiz",
            subject = "Chemistry",
            difficulty = "Beginner",
            questionCount = 15,
            timeLimit = 20,
            averageScore = 82.1f,
            isBookmarked = true
        ),
        QuizItem(
            id = "4",
            title = "Biology Practice Test",
            subject = "Biology",
            difficulty = "Intermediate",
            questionCount = 25,
            timeLimit = 45,
            averageScore = 71.8f,
            isBookmarked = false
        )
    )
}