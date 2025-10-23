package com.studyhub.tanzania.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.ui.screens.auth.AuthScreen
import com.studyhub.tanzania.ui.screens.home.HomeScreen
import com.studyhub.tanzania.ui.screens.subjects.SubjectsScreen
import com.studyhub.tanzania.ui.screens.notes.NotesScreen
import com.studyhub.tanzania.ui.screens.quiz.QuizScreen
import com.studyhub.tanzania.ui.screens.pastpapers.PastPapersScreen
import com.studyhub.tanzania.ui.screens.progress.ProgressScreen
import com.studyhub.tanzania.ui.screens.settings.SettingsScreen

/**
 * Navigation setup for Study Hub app
 */
@Composable
fun StudyHubNavigation(
    navController: NavHostController,
    isUserAuthenticated: Boolean,
    currentUser: User?
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserAuthenticated) "home" else "auth"
    ) {
        composable("auth") {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                currentUser = currentUser,
                onNavigateToSubjects = {
                    navController.navigate("subjects")
                },
                onNavigateToNotes = {
                    navController.navigate("notes")
                },
                onNavigateToQuiz = {
                    navController.navigate("quiz")
                },
                onNavigateToPastPapers = {
                    navController.navigate("pastpapers")
                },
                onNavigateToProgress = {
                    navController.navigate("progress")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }
        
        composable("subjects") {
            SubjectsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNotes = { subjectId ->
                    navController.navigate("notes/$subjectId")
                }
            )
        }
        
        composable("notes") {
            NotesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("notes/{subjectId}") { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            NotesScreen(
                subjectId = subjectId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("quiz") {
            QuizScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("pastpapers") {
            PastPapersScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("progress") {
            ProgressScreen(
                currentUser = currentUser,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                currentUser = currentUser,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSignOut = {
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}