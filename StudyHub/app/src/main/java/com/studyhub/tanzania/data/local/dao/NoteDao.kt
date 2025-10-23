package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.Note
import com.studyhub.tanzania.data.models.DifficultyLevel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Note entity
 */
@Dao
interface NoteDao {
    
    @Query("SELECT * FROM note ORDER BY createdDate DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE subjectId = :subjectId ORDER BY createdDate DESC")
    fun getNotesBySubject(subjectId: String): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE topicId = :topicId ORDER BY createdDate DESC")
    fun getNotesByTopic(topicId: String): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: String): Note?
    
    @Query("SELECT * FROM note WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' OR titleSwahili LIKE '%' || :query || '%' OR contentSwahili LIKE '%' || :query || '%' ORDER BY createdDate DESC")
    fun searchNotes(query: String): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE difficulty = :difficulty ORDER BY createdDate DESC")
    fun getNotesByDifficulty(difficulty: DifficultyLevel): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE isBookmarked = 1 ORDER BY createdDate DESC")
    fun getBookmarkedNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE isOfflineAvailable = 1 ORDER BY createdDate DESC")
    suspend fun getOfflineNotes(): List<Note>
    
    @Query("SELECT * FROM note WHERE tags LIKE '%' || :tag || '%' ORDER BY createdDate DESC")
    fun getNotesByTag(tag: String): Flow<List<Note>>
    
    @Query("SELECT * FROM note ORDER BY createdDate DESC LIMIT :limit")
    fun getRecentNotes(limit: Int): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE isPremium = 0 ORDER BY createdDate DESC")
    fun getFreeNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM note WHERE isPremium = 1 ORDER BY createdDate DESC")
    fun getPremiumNotes(): Flow<List<Note>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)
    
    @Update
    suspend fun updateNote(note: Note)
    
    @Delete
    suspend fun deleteNote(note: Note)
    
    @Query("UPDATE note SET isBookmarked = :isBookmarked WHERE id = :noteId")
    suspend fun updateBookmarkStatus(noteId: String, isBookmarked: Boolean)
    
    @Query("UPDATE note SET rating = :rating WHERE id = :noteId")
    suspend fun updateRating(noteId: String, rating: Float)
    
    @Query("UPDATE note SET viewCount = viewCount + 1 WHERE id = :noteId")
    suspend fun incrementViewCount(noteId: String)
    
    @Query("UPDATE note SET isOfflineAvailable = :isOfflineAvailable WHERE id = :noteId")
    suspend fun updateOfflineAvailability(noteId: String, isOfflineAvailable: Boolean)
    
    @Query("SELECT COUNT(*) FROM note WHERE subjectId = :subjectId")
    suspend fun getNoteCountBySubject(subjectId: String): Int
    
    @Query("SELECT COUNT(*) FROM note WHERE isBookmarked = 1")
    suspend fun getBookmarkedNoteCount(): Int
}