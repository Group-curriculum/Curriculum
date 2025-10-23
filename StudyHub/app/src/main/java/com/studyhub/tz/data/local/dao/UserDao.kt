package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE uid = :userId")
    fun getUser(userId: String): LiveData<User?>

    @Query("SELECT * FROM users WHERE uid = :userId")
    suspend fun getUserSync(userId: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
