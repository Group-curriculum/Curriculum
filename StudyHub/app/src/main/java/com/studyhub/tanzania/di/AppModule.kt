package com.studyhub.tanzania.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.studyhub.tanzania.data.local.database.StudyHubDatabase
import com.studyhub.tanzania.data.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin modules for dependency injection
 */
object AppModule {
    
    val modules = module {
        
        // Firebase
        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
        single { FirebaseStorage.getInstance() }
        
        // Database
        single { StudyHubDatabase.getDatabase(androidContext()) }
        
        // DAOs
        single { get<StudyHubDatabase>().userDao() }
        single { get<StudyHubDatabase>().subjectDao() }
        single { get<StudyHubDatabase>().noteDao() }
        single { get<StudyHubDatabase>().quizDao() }
        single { get<StudyHubDatabase>().pastPaperDao() }
        single { get<StudyHubDatabase>().progressDao() }
        single { get<StudyHubDatabase>().quizAttemptDao() }
        single { get<StudyHubDatabase>().pastPaperAttemptDao() }
        single { get<StudyHubDatabase>().studySessionDao() }
        single { get<StudyHubDatabase>().achievementDao() }
        
        // Repositories
        single { UserRepository(get(), get(), get()) }
        single { SubjectRepository(get(), get()) }
        single { NoteRepository(get(), get()) }
        single { QuizRepository(get(), get()) }
        single { PastPaperRepository(get(), get()) }
        single { ProgressRepository(get(), get()) }
        single { StudySessionRepository(get(), get()) }
        single { AchievementRepository(get(), get()) }
    }
}