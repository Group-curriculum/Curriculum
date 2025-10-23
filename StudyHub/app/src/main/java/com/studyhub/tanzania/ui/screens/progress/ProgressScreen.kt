package com.studyhub.tanzania.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.ui.components.BottomNavigationBar

/**
 * Progress screen for Study Hub app
 */
@Composable
fun ProgressScreen(
    currentUser: User?,
    onNavigateBack: () -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("Week") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Your Progress",
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
            // Study Streak Card
            item {
                StudyStreakCard(
                    streak = currentUser?.studyStreak ?: 0,
                    totalStudyTime = currentUser?.totalStudyTime ?: 0
                )
            }
            
            // Period Selection
            item {
                Text(
                    text = "View Progress",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("Week", "Month", "Year")) { period ->
                        FilterChip(
                            onClick = { selectedPeriod = period },
                            label = { Text(period) },
                            selected = selectedPeriod == period,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // Progress Stats
            item {
                Text(
                    text = "Study Statistics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getProgressStats()) { stat ->
                        StatCard(
                            title = stat.title,
                            value = stat.value,
                            icon = stat.icon,
                            color = stat.color
                        )
                    }
                }
            }
            
            // Subject Progress
            item {
                Text(
                    text = "Subject Progress",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(getSubjectProgress()) { subject ->
                SubjectProgressCard(
                    subject = subject.subject,
                    progress = subject.progress,
                    notesRead = subject.notesRead,
                    quizzesPassed = subject.quizzesPassed,
                    averageScore = subject.averageScore
                )
            }
            
            // Achievements
            item {
                Text(
                    text = "Recent Achievements",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(getRecentAchievements()) { achievement ->
                AchievementCard(
                    title = achievement.title,
                    description = achievement.description,
                    icon = achievement.icon,
                    isUnlocked = achievement.isUnlocked
                )
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "progress",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

@Composable
fun StudyStreakCard(
    streak: Int,
    totalStudyTime: Long
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Study Streak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Text(
                    text = "$streak days",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Keep it up! 🔥",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Streak",
                    tint = Color(0xFFFF5722),
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${totalStudyTime}min",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Text(
                    text = "Total Study Time",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
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
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = title,
                fontSize = 12.sp,
                color = color,
                maxLines = 2
            )
        }
    }
}

@Composable
fun SubjectProgressCard(
    subject: String,
    progress: Float,
    notesRead: Int,
    quizzesPassed: Int,
    averageScore: Float
) {
    Card(
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notes: $notesRead",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Text(
                    text = "Quizzes: $quizzesPassed",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Text(
                    text = "Avg: ${String.format("%.1f", averageScore)}%",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun AchievementCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isUnlocked: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = if (isUnlocked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
            
            if (isUnlocked) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Unlocked",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

data class ProgressStat(
    val title: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

data class SubjectProgressItem(
    val subject: String,
    val progress: Float,
    val notesRead: Int,
    val quizzesPassed: Int,
    val averageScore: Float
)

data class AchievementItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isUnlocked: Boolean
)

fun getProgressStats(): List<ProgressStat> {
    return listOf(
        ProgressStat(
            title = "Notes Read",
            value = "24",
            icon = Icons.Default.Book,
            color = Color(0xFF2196F3)
        ),
        ProgressStat(
            title = "Quizzes Passed",
            value = "12",
            icon = Icons.Default.Quiz,
            color = Color(0xFF4CAF50)
        ),
        ProgressStat(
            title = "Study Hours",
            value = "45",
            icon = Icons.Default.Schedule,
            color = Color(0xFFFF9800)
        ),
        ProgressStat(
            title = "Achievements",
            value = "8",
            icon = Icons.Default.Star,
            color = Color(0xFF9C27B0)
        )
    )
}

fun getSubjectProgress(): List<SubjectProgressItem> {
    return listOf(
        SubjectProgressItem(
            subject = "Mathematics",
            progress = 0.75f,
            notesRead = 8,
            quizzesPassed = 5,
            averageScore = 82.5f
        ),
        SubjectProgressItem(
            subject = "Physics",
            progress = 0.60f,
            notesRead = 6,
            quizzesPassed = 3,
            averageScore = 78.2f
        ),
        SubjectProgressItem(
            subject = "Chemistry",
            progress = 0.45f,
            notesRead = 4,
            quizzesPassed = 2,
            averageScore = 75.8f
        ),
        SubjectProgressItem(
            subject = "Biology",
            progress = 0.30f,
            notesRead = 3,
            quizzesPassed = 1,
            averageScore = 72.1f
        )
    )
}

fun getRecentAchievements(): List<AchievementItem> {
    return listOf(
        AchievementItem(
            title = "First Quiz Passed",
            description = "Completed your first quiz successfully",
            icon = Icons.Default.Quiz,
            isUnlocked = true
        ),
        AchievementItem(
            title = "Study Streak",
            description = "Studied for 7 consecutive days",
            icon = Icons.Default.LocalFireDepartment,
            isUnlocked = true
        ),
        AchievementItem(
            title = "Note Reader",
            description = "Read 20 study notes",
            icon = Icons.Default.Book,
            isUnlocked = true
        ),
        AchievementItem(
            title = "Perfect Score",
            description = "Get 100% on any quiz",
            icon = Icons.Default.Star,
            isUnlocked = false
        )
    )
}