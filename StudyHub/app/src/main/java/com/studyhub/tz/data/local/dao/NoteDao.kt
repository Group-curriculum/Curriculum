package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE subjectId = :subjectId ORDER BY `order` ASC")
    fun getNotesBySubject(subjectId: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: String): LiveData<Note?>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteByIdSync(noteId: String): Note?

    @Query("SELECT * FROM notes WHERE isBookmarked = 1 ORDER BY updatedAt DESC")
    fun getBookmarkedNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY readCount DESC LIMIT :limit")
    fun getPopularNotes(limit: Int = 10): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes WHERE subjectId = :subjectId")
    suspend fun deleteNotesBySubject(subjectId: String)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}
