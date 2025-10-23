package com.studyhub.tz.data.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.UserDao
import com.studyhub.tz.data.model.User
import kotlinx.coroutines.tasks.await

/**
 * Repository for authentication operations
 */
class AuthRepository(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao
) {
    companion object {
        private const val USERS_COLLECTION = "users"
    }

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    fun isUserLoggedIn(): Boolean = currentUser != null

    /**
     * Sign in with email and password
     */
    suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User not found")
            
            // Fetch user data from Firestore
            val user = getUserFromFirestore(firebaseUser.uid)
            userDao.insertUser(user)
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign up with email and password
     */
    suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String
    ): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User creation failed")
            
            // Create user document in Firestore
            val user = User(
                uid = firebaseUser.uid,
                email = email,
                displayName = displayName,
                createdAt = System.currentTimeMillis(),
                lastLoginAt = System.currentTimeMillis()
            )
            
            firestore.collection(USERS_COLLECTION)
                .document(firebaseUser.uid)
                .set(user)
                .await()
            
            userDao.insertUser(user)
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get Google Sign-In client
     */
    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_WEB_CLIENT_ID") // Replace with actual web client ID from Firebase
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    /**
     * Sign in with Google account
     */
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: throw Exception("Google sign-in failed")
            
            // Check if user exists in Firestore, create if not
            val userDoc = firestore.collection(USERS_COLLECTION)
                .document(firebaseUser.uid)
                .get()
                .await()
            
            val user = if (userDoc.exists()) {
                userDoc.toObject(User::class.java) ?: throw Exception("Failed to parse user data")
            } else {
                val newUser = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = firebaseUser.displayName ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString() ?: "",
                    createdAt = System.currentTimeMillis(),
                    lastLoginAt = System.currentTimeMillis()
                )
                
                firestore.collection(USERS_COLLECTION)
                    .document(firebaseUser.uid)
                    .set(newUser)
                    .await()
                
                newUser
            }
            
            userDao.insertUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign out
     */
    suspend fun signOut() {
        firebaseAuth.signOut()
        getGoogleSignInClient().signOut().await()
        userDao.deleteAllUsers()
    }

    /**
     * Reset password
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get user from Firestore
     */
    private suspend fun getUserFromFirestore(uid: String): User {
        val document = firestore.collection(USERS_COLLECTION)
            .document(uid)
            .get()
            .await()
        
        return document.toObject(User::class.java) 
            ?: throw Exception("User not found in Firestore")
    }

    /**
     * Update user profile
     */
    suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            firestore.collection(USERS_COLLECTION)
                .document(user.uid)
                .set(user)
                .await()
            
            userDao.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
