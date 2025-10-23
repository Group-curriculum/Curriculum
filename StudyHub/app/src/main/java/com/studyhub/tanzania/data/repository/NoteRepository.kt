package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.NoteDao
import com.studyhub.tanzania.data.models.Note
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.data.models.DifficultyLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing study notes
 */
@Singleton
class NoteRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val noteDao: NoteDao
) {
    
    /**
     * Get all notes from local database
     */
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    
    /**
     * Get notes by subject
     */
    fun getNotesBySubject(subjectId: String): Flow<List<Note>> = 
        noteDao.getNotesBySubject(subjectId)
    
    /**
     * Get notes by topic
     */
    fun getNotesByTopic(topicId: String): Flow<List<Note>> = 
        noteDao.getNotesByTopic(topicId)
    
    /**
     * Get note by ID
     */
    suspend fun getNoteById(noteId: String): Note? = 
        noteDao.getNoteById(noteId)
    
    /**
     * Search notes by query
     */
    fun searchNotes(query: String): Flow<List<Note>> = 
        noteDao.searchNotes(query)
    
    /**
     * Get notes by difficulty level
     */
    fun getNotesByDifficulty(difficulty: DifficultyLevel): Flow<List<Note>> = 
        noteDao.getNotesByDifficulty(difficulty)
    
    /**
     * Get bookmarked notes
     */
    fun getBookmarkedNotes(): Flow<List<Note>> = 
        noteDao.getBookmarkedNotes()
    
    /**
     * Get offline notes
     */
    suspend fun getOfflineNotes(): List<Note> = 
        noteDao.getOfflineNotes()
    
    /**
     * Sync notes from Firestore
     */
    suspend fun syncNotesFromFirestore(subjectId: String? = null): Result<Unit> {
        return try {
            val query = if (subjectId != null) {
                firestore.collection("notes")
                    .whereEqualTo("subjectId", subjectId)
            } else {
                firestore.collection("notes")
            }
            
            val notes = query.get().await().toObjects(Note::class.java)
            noteDao.insertNotes(notes)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Bookmark/unbookmark a note
     */
    suspend fun toggleBookmark(noteId: String): Result<Boolean> {
        return try {
            val note = noteDao.getNoteById(noteId)
            if (note != null) {
                val updatedNote = note.copy(isBookmarked = !note.isBookmarked)
                noteDao.insertNote(updatedNote)
                Result.success(updatedNote.isBookmarked)
            } else {
                Result.failure(Exception("Note not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update note rating
     */
    suspend fun updateNoteRating(noteId: String, rating: Float): Result<Unit> {
        return try {
            val note = noteDao.getNoteById(noteId)
            if (note != null) {
                val updatedNote = note.copy(rating = rating)
                noteDao.insertNote(updatedNote)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Note not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment note view count
     */
    suspend fun incrementViewCount(noteId: String): Result<Unit> {
        return try {
            val note = noteDao.getNoteById(noteId)
            if (note != null) {
                val updatedNote = note.copy(viewCount = note.viewCount + 1)
                noteDao.insertNote(updatedNote)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Note not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Download note for offline use
     */
    suspend fun downloadNoteForOffline(noteId: String): Result<Unit> {
        return try {
            val note = noteDao.getNoteById(noteId)
            if (note != null) {
                val updatedNote = note.copy(isOfflineAvailable = true)
                noteDao.insertNote(updatedNote)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Note not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get notes by tags
     */
    fun getNotesByTags(tags: List<String>): Flow<List<Note>> = 
        noteDao.getNotesByTags(tags)
    
    /**
     * Get recent notes
     */
    fun getRecentNotes(limit: Int = 10): Flow<List<Note>> = 
        noteDao.getRecentNotes(limit)
}