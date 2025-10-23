package com.studyhub.tanzania.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.ui.components.BottomNavigationBar
import com.studyhub.tanzania.ui.components.QuickActionCard
import com.studyhub.tanzania.ui.components.StudyStreakCard
import com.studyhub.tanzania.ui.components.SubjectCard

/**
 * Home screen for Study Hub app
 */
@Composable
fun HomeScreen(
    currentUser: User?,
    onNavigateToSubjects: () -> Unit,
    onNavigateToNotes: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToPastPapers: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Karibu, ${currentUser?.displayName ?: "Student"}!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Ready to study?",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            },
            actions = {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
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
            
            // Recent Subjects
            item {
                Text(
                    text = "Recent Subjects",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getRecentSubjects()) { subject ->
                        SubjectCard(
                            title = subject.title,
                            subtitle = subject.subtitle,
                            icon = subject.icon,
                            color = subject.color,
                            onClick = subject.onClick
                        )
                    }
                }
            }
            
            // Study Tips
            item {
                Text(
                    text = "Study Tips",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                StudyTipsCard()
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "home",
            onNavigateToHome = { },
            onNavigateToSubjects = onNavigateToSubjects,
            onNavigateToQuiz = onNavigateToQuiz,
            onNavigateToPastPapers = onNavigateToPastPapers,
            onNavigateToProgress = onNavigateToProgress
        )
    }
}

@Composable
fun StudyTipsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "💡 Study Tip of the Day",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Take regular breaks every 25-30 minutes to maintain focus and improve retention. The Pomodoro Technique works wonders!",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                lineHeight = 20.sp
            )
        }
    }
}

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

data class RecentSubject(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

fun getQuickActions(): List<QuickAction> {
    return listOf(
        QuickAction(
            title = "Take Quiz",
            icon = Icons.Default.Quiz,
            color = Color(0xFF4CAF50),
            onClick = { }
        ),
        QuickAction(
            title = "Past Papers",
            icon = Icons.Default.Assignment,
            color = Color(0xFF2196F3),
            onClick = { }
        ),
        QuickAction(
            title = "Study Notes",
            icon = Icons.Default.Book,
            color = Color(0xFFFF9800),
            onClick = { }
        ),
        QuickAction(
            title = "Progress",
            icon = Icons.Default.TrendingUp,
            color = Color(0xFF9C27B0),
            onClick = { }
        )
    )
}

fun getRecentSubjects(): List<RecentSubject> {
    return listOf(
        RecentSubject(
            title = "Mathematics",
            subtitle = "O-Level",
            icon = Icons.Default.Calculate,
            color = Color(0xFF2196F3),
            onClick = { }
        ),
        RecentSubject(
            title = "Physics",
            subtitle = "O-Level",
            icon = Icons.Default.Science,
            color = Color(0xFF4CAF50),
            onClick = { }
        ),
        RecentSubject(
            title = "Chemistry",
            subtitle = "O-Level",
            icon = Icons.Default.Science,
            color = Color(0xFFFF9800),
            onClick = { }
        )
    )
}