package com.turtlemint.assignment.data.db.dao

import androidx.room.*
import com.turtlemint.assignment.data.db.entity.Issue
import kotlinx.coroutines.flow.Flow

/**
 * IssueDao manages all the database queries.
 */
@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(issues: List<Issue>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(issue: Issue): Long

    @Query("SELECT * FROM issue")
    fun fetchIssues(): Flow<List<Issue>>

    @Update
    suspend fun update(issue: Issue)
}