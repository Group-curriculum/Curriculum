package com.studyhub.tanzania.ui.screens.settings

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.ui.components.BottomNavigationBar

/**
 * Settings screen for Study Hub app
 */
@Composable
fun SettingsScreen(
    currentUser: User?,
    onNavigateBack: () -> Unit,
    onSignOut: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var offlineModeEnabled by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
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
            // User Profile Section
            item {
                UserProfileSection(
                    user = currentUser,
                    onEditProfile = { /* TODO: Navigate to edit profile */ }
                )
            }
            
            // Preferences Section
            item {
                Text(
                    text = "Preferences",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                SettingsItem(
                    title = "Notifications",
                    subtitle = "Study reminders and updates",
                    icon = Icons.Default.Notifications,
                    trailing = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }
                )
            }
            
            item {
                SettingsItem(
                    title = "Dark Mode",
                    subtitle = "Use dark theme",
                    icon = Icons.Default.DarkMode,
                    trailing = {
                        Switch(
                            checked = darkModeEnabled,
                            onCheckedChange = { darkModeEnabled = it }
                        )
                    }
                )
            }
            
            item {
                SettingsItem(
                    title = "Offline Mode",
                    subtitle = "Download content for offline use",
                    icon = Icons.Default.OfflinePin,
                    trailing = {
                        Switch(
                            checked = offlineModeEnabled,
                            onCheckedChange = { offlineModeEnabled = it }
                        )
                    }
                )
            }
            
            // Study Settings Section
            item {
                Text(
                    text = "Study Settings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                SettingsItem(
                    title = "Study Reminders",
                    subtitle = "Set daily study reminders",
                    icon = Icons.Default.Schedule,
                    onClick = { /* TODO: Navigate to reminders */ }
                )
            }
            
            item {
                SettingsItem(
                    title = "Font Size",
                    subtitle = "Adjust text size",
                    icon = Icons.Default.TextFields,
                    onClick = { /* TODO: Navigate to font settings */ }
                )
            }
            
            item {
                SettingsItem(
                    title = "Language",
                    subtitle = "English / Kiswahili",
                    icon = Icons.Default.Language,
                    onClick = { /* TODO: Navigate to language settings */ }
                )
            }
            
            // Account Section
            item {
                Text(
                    text = "Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                SettingsItem(
                    title = "Subscription",
                    subtitle = if (currentUser?.isPremium == true) "Premium Active" else "Upgrade to Premium",
                    icon = Icons.Default.Star,
                    onClick = { /* TODO: Navigate to subscription */ }
                )
            }
            
            item {
                SettingsItem(
                    title = "Privacy Policy",
                    subtitle = "View privacy policy",
                    icon = Icons.Default.PrivacyTip,
                    onClick = { /* TODO: Open privacy policy */ }
                )
            }
            
            item {
                SettingsItem(
                    title = "Terms of Service",
                    subtitle = "View terms of service",
                    icon = Icons.Default.Description,
                    onClick = { /* TODO: Open terms of service */ }
                )
            }
            
            item {
                SettingsItem(
                    title = "About",
                    subtitle = "App version and info",
                    icon = Icons.Default.Info,
                    onClick = { /* TODO: Navigate to about */ }
                )
            }
            
            // Sign Out
            item {
                SettingsItem(
                    title = "Sign Out",
                    subtitle = "Sign out of your account",
                    icon = Icons.Default.Logout,
                    textColor = Color(0xFFF44336),
                    onClick = onSignOut
                )
            }
        }
        
        // Bottom Navigation
        BottomNavigationBar(
            currentRoute = "settings",
            onNavigateToHome = { },
            onNavigateToSubjects = { },
            onNavigateToQuiz = { },
            onNavigateToPastPapers = { },
            onNavigateToProgress = { }
        )
    }
}

@Composable
fun UserProfileSection(
    user: User?,
    onEditProfile: () -> Unit
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user?.displayName ?: "Student",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Text(
                    text = user?.email ?: "student@example.com",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Text(
                    text = if (user?.level == com.studyhub.tanzania.data.models.EducationLevel.O_LEVEL) "O-Level Student" else "A-Level Student",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            
            IconButton(onClick = onEditProfile) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Card(
        onClick = { onClick?.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
            
            if (trailing != null) {
                trailing()
            } else if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}