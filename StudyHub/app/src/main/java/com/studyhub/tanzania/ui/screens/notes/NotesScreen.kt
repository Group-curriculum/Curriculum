package com.studyhub.tanzania.ui.screens.notes

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
import com.studyhub.tanzania.ui.components.BottomNavigationBar

/**
 * Notes screen for Study Hub app
 */
@Composable
fun NotesScreen(
    subjectId: String = "",
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
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
                    text = "Study Notes",
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
            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search notes...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            
            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "Recent", "Bookmarked", "Offline")) { filter ->
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
            
            // Notes List
            items(getSampleNotes()) { note ->
                NoteCard(
                    title = note.title,
                    subject = note.subject,
                    topic = note.topic,
                    difficulty = note.difficulty,
                    readTime = note.readTime,
                    isBookmarked = note.isBookmarked,
                    isOffline = note.isOffline,
                    onClick = { /* TODO: Navigate to note detail */ }
                )
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "notes",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

@Composable
fun NoteCard(
    title: String,
    subject: String,
    topic: String,
    difficulty: String,
    readTime: String,
    isBookmarked: Boolean,
    isOffline: Boolean,
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
                        text = "$subject • $topic",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DifficultyChip(difficulty = difficulty)
                        ReadTimeChip(readTime = readTime)
                        if (isOffline) {
                            OfflineChip()
                        }
                    }
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
fun DifficultyChip(difficulty: String) {
    val color = when (difficulty) {
        "Beginner" -> Color(0xFF4CAF50)
        "Intermediate" -> Color(0xFFFF9800)
        "Advanced" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = difficulty,
            fontSize = 12.sp,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ReadTimeChip(readTime: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = readTime,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun OfflineChip() {
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
                contentDescription = "Offline",
                modifier = Modifier.size(12.dp),
                tint = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Offline",
                fontSize = 12.sp,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

data class NoteItem(
    val id: String,
    val title: String,
    val subject: String,
    val topic: String,
    val difficulty: String,
    val readTime: String,
    val isBookmarked: Boolean,
    val isOffline: Boolean
)

fun getSampleNotes(): List<NoteItem> {
    return listOf(
        NoteItem(
            id = "1",
            title = "Introduction to Algebra",
            subject = "Mathematics",
            topic = "Algebra",
            difficulty = "Beginner",
            readTime = "5 min",
            isBookmarked = true,
            isOffline = true
        ),
        NoteItem(
            id = "2",
            title = "Newton's Laws of Motion",
            subject = "Physics",
            topic = "Mechanics",
            difficulty = "Intermediate",
            readTime = "8 min",
            isBookmarked = false,
            isOffline = true
        ),
        NoteItem(
            id = "3",
            title = "Chemical Bonding",
            subject = "Chemistry",
            topic = "Atomic Structure",
            difficulty = "Advanced",
            readTime = "12 min",
            isBookmarked = true,
            isOffline = false
        ),
        NoteItem(
            id = "4",
            title = "Cell Structure and Function",
            subject = "Biology",
            topic = "Cell Biology",
            difficulty = "Intermediate",
            readTime = "10 min",
            isBookmarked = false,
            isOffline = true
        ),
        NoteItem(
            id = "5",
            title = "Grammar Rules",
            subject = "English",
            topic = "Grammar",
            difficulty = "Beginner",
            readTime = "6 min",
            isBookmarked = true,
            isOffline = false
        )
    )
}