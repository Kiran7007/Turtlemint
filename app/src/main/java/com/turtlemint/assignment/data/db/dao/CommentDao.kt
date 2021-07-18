package com.turtlemint.assignment.data.db.dao

import androidx.room.*
import com.turtlemint.assignment.data.db.entity.Comment
import kotlinx.coroutines.flow.Flow

/**
 * CommentDao manages all the database queries.
 */
@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(issues: List<Comment>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment): Long

    @Query("SELECT * FROM comment")
    fun fetchComments(): Flow<List<Comment>>

    @Update
    suspend fun update(comment: Comment)
}