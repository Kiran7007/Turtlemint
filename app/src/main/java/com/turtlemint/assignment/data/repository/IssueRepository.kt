package com.turtlemint.assignment.data.repository

import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.db.entity.Issue
import kotlinx.coroutines.flow.Flow

/**
 * IssueRepository manages the transactions of data layer and network layer.
 */
interface IssueRepository {

    /**
     * Fetch the data from the remote server.
     */
    suspend fun fetchRemoteIssues(): Result<List<Issue>>

    /**
     * Fetch the data from local database
     */
    fun fetchDataFromDB(): Flow<List<Issue>>

    /**
     * Update the issue entity in the database.
     */
    suspend fun update(issue: Issue)

    /**
     * Insert the list of issues in the database.
     */
    suspend fun insert(data: List<Issue>): LongArray
}