package com.turtlemint.assignment.data.repository

import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.db.entity.Comment
import kotlinx.coroutines.flow.Flow

/**
 * CommentRepository manages the transactions of data layer and network layer.
 */
interface CommentRepository {

    /**
     * Fetch the data from the remote server.
     */
    suspend fun fetchRemoteComments(url: String): Result<List<Comment>>

    /**
     * Fetch the data from local database
     */
    fun fetchDataFromDB(): Flow<List<Comment>>

    /**
     * Update the comment entity in the database.
     */
    suspend fun update(comment: Comment)

    /**
     * Insert the list of issues in the database.
     */
    suspend fun insert(data: List<Comment>): LongArray
}