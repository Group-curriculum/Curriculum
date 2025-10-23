package com.studyhub.tanzania.ui.screens.pastpapers

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
 * Past Papers screen for Study Hub app
 */
@Composable
fun PastPapersScreen(
    onNavigateBack: () -> Unit
) {
    var selectedYear by remember { mutableStateOf("All") }
    var selectedSubject by remember { mutableStateOf("All") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Past Papers",
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
            actions = {
                IconButton(onClick = { /* TODO: Implement search */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
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
            // Year Filter
            item {
                Text(
                    text = "Filter by Year",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "2023", "2022", "2021", "2020", "2019")) { year ->
                        FilterChip(
                            onClick = { selectedYear = year },
                            label = { Text(year) },
                            selected = selectedYear == year,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // Subject Filter
            item {
                Text(
                    text = "Filter by Subject",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "Mathematics", "Physics", "Chemistry", "Biology", "English")) { subject ->
                        FilterChip(
                            onClick = { selectedSubject = subject },
                            label = { Text(subject) },
                            selected = selectedSubject == subject,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // Past Papers List
            items(getSamplePastPapers()) { paper ->
                PastPaperCard(
                    title = paper.title,
                    subject = paper.subject,
                    year = paper.year,
                    month = paper.month,
                    duration = paper.duration,
                    totalMarks = paper.totalMarks,
                    isDownloaded = paper.isDownloaded,
                    isPremium = paper.isPremium,
                    onClick = { /* TODO: Navigate to past paper */ }
                )
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "pastpapers",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

@Composable
fun PastPaperCard(
    title: String,
    subject: String,
    year: Int,
    month: String,
    duration: Int,
    totalMarks: Int,
    isDownloaded: Boolean,
    isPremium: Boolean,
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
                        text = "$subject • $month $year",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DurationChip(duration = duration)
                        MarksChip(marks = totalMarks)
                        if (isDownloaded) {
                            DownloadedChip()
                        }
                        if (isPremium) {
                            PremiumChip()
                        }
                    }
                }
                
                IconButton(
                    onClick = { /* TODO: Download/View options */ }
                ) {
                    Icon(
                        imageVector = if (isDownloaded) Icons.Default.Visibility else Icons.Default.Download,
                        contentDescription = if (isDownloaded) "View" else "Download",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun DurationChip(duration: Int) {
    Surface(
        color = Color(0xFF2196F3).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "${duration}min",
            fontSize = 12.sp,
            color = Color(0xFF2196F3),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun MarksChip(marks: Int) {
    Surface(
        color = Color(0xFF4CAF50).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "${marks} marks",
            fontSize = 12.sp,
            color = Color(0xFF4CAF50),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun DownloadedChip() {
    Surface(
        color = Color(0xFF4CAF50).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.OfflinePin,
                contentDescription = "Downloaded",
                modifier = Modifier.size(12.dp),
                tint = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Downloaded",
                fontSize = 12.sp,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun PremiumChip() {
    Surface(
        color = Color(0xFFFF9800).copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Premium",
                modifier = Modifier.size(12.dp),
                tint = Color(0xFFFF9800)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Premium",
                fontSize = 12.sp,
                color = Color(0xFFFF9800)
            )
        }
    }
}

data class PastPaperItem(
    val id: String,
    val title: String,
    val subject: String,
    val year: Int,
    val month: String,
    val duration: Int,
    val totalMarks: Int,
    val isDownloaded: Boolean,
    val isPremium: Boolean
)

fun getSamplePastPapers(): List<PastPaperItem> {
    return listOf(
        PastPaperItem(
            id = "1",
            title = "Mathematics Paper 1",
            subject = "Mathematics",
            year = 2023,
            month = "November",
            duration = 180,
            totalMarks = 100,
            isDownloaded = true,
            isPremium = false
        ),
        PastPaperItem(
            id = "2",
            title = "Physics Paper 1",
            subject = "Physics",
            year = 2023,
            month = "November",
            duration = 180,
            totalMarks = 100,
            isDownloaded = false,
            isPremium = false
        ),
        PastPaperItem(
            id = "3",
            title = "Chemistry Paper 1",
            subject = "Chemistry",
            year = 2022,
            month = "November",
            duration = 180,
            totalMarks = 100,
            isDownloaded = true,
            isPremium = true
        ),
        PastPaperItem(
            id = "4",
            title = "Biology Paper 1",
            subject = "Biology",
            year = 2022,
            month = "November",
            duration = 180,
            totalMarks = 100,
            isDownloaded = false,
            isPremium = false
        ),
        PastPaperItem(
            id = "5",
            title = "English Paper 1",
            subject = "English",
            year = 2021,
            month = "November",
            duration = 180,
            totalMarks = 100,
            isDownloaded = true,
            isPremium = false
        )
    )
}