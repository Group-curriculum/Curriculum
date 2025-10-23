package com.studyhub.tz.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.NoteDao
import com.studyhub.tz.data.model.Note
import kotlinx.coroutines.tasks.await

/**
 * Repository for notes with Firebase sync and offline caching
 */
class NoteRepository(
    private val firestore: FirebaseFirestore,
    private val noteDao: NoteDao
) {
    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    /**
     * Get notes by subject from local database
     */
    fun getNotesBySubject(subjectId: String): LiveData<List<Note>> {
        return noteDao.getNotesBySubject(subjectId)
    }

    /**
     * Get note by ID
     */
    fun getNoteById(noteId: String): LiveData<Note?> {
        return noteDao.getNoteById(noteId)
    }

    /**
     * Get bookmarked notes
     */
    fun getBookmarkedNotes(): LiveData<List<Note>> {
        return noteDao.getBookmarkedNotes()
    }

    /**
     * Get popular notes
     */
    fun getPopularNotes(limit: Int = 10): LiveData<List<Note>> {
        return noteDao.getPopularNotes(limit)
    }

    /**
     * Sync notes for a subject from Firebase
     */
    suspend fun syncNotesForSubject(subjectId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection(NOTES_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .get()
                .await()
            
            val notes = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Note::class.java)?.copy(id = doc.id)
            }
            
            noteDao.insertNotes(notes)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Toggle bookmark status
     */
    suspend fun toggleBookmark(noteId: String): Result<Unit> {
        return try {
            val note = noteDao.getNoteByIdSync(noteId) ?: throw Exception("Note not found")
            val updatedNote = note.copy(isBookmarked = !note.isBookmarked)
            noteDao.updateNote(updatedNote)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Increment read count
     */
    suspend fun incrementReadCount(noteId: String): Result<Unit> {
        return try {
            val note = noteDao.getNoteByIdSync(noteId) ?: throw Exception("Note not found")
            val updatedNote = note.copy(readCount = note.readCount + 1)
            noteDao.updateNote(updatedNote)
            
            // Also update in Firebase
            firestore.collection(NOTES_COLLECTION)
                .document(noteId)
                .update("readCount", updatedNote.readCount)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
