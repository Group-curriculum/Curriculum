package com.studyhub.tanzania.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.UserDao
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.data.models.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing user data and authentication
 */
@Singleton
class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao
) {
    
    /**
     * Get current user from local database
     */
    fun getCurrentUser(): Flow<User?> = userDao.getCurrentUser()
    
    /**
     * Sign in with email and password
     */
    suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                val userData = getUserFromFirestore(user.uid)
                userDao.insertUser(userData)
                Result.success(userData)
            } else {
                Result.failure(Exception("Sign in failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Sign up with email and password
     */
    suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                val userData = User(
                    id = user.uid,
                    email = email,
                    displayName = displayName,
                    level = EducationLevel.O_LEVEL
                )
                saveUserToFirestore(userData)
                userDao.insertUser(userData)
                Result.success(userData)
            } else {
                Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Sign in with Google
     */
    suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user
            if (user != null) {
                val userData = getUserFromFirestore(user.uid) ?: User(
                    id = user.uid,
                    email = user.email ?: "",
                    displayName = user.displayName ?: "",
                    photoUrl = user.photoUrl?.toString() ?: "",
                    level = EducationLevel.O_LEVEL
                )
                saveUserToFirestore(userData)
                userDao.insertUser(userData)
                Result.success(userData)
            } else {
                Result.failure(Exception("Google sign in failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Sign out current user
     */
    suspend fun signOut() {
        firebaseAuth.signOut()
        userDao.clearCurrentUser()
    }
    
    /**
     * Update user profile
     */
    suspend fun updateUser(user: User): Result<User> {
        return try {
            saveUserToFirestore(user)
            userDao.insertUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update user preferences
     */
    suspend fun updateUserPreferences(userId: String, preferences: UserPreferences): Result<UserPreferences> {
        return try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                val updatedUser = user.copy(preferences = preferences)
                saveUserToFirestore(updatedUser)
                userDao.insertUser(updatedUser)
                Result.success(preferences)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get user from Firestore
     */
    private suspend fun getUserFromFirestore(userId: String): User? {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                document.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Save user to Firestore
     */
    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.id).set(user).await()
    }
    
    /**
     * Check if user is signed in
     */
    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
    
    /**
     * Get current user ID
     */
    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}