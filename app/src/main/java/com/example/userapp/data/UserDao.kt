package com.example.userapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.userapp.model.User


@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)
}