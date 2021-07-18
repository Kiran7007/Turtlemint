package com.turtlemint.assignment.data.repository

import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.api.ApiService
import com.turtlemint.assignment.data.db.dao.CommentDao
import com.turtlemint.assignment.data.db.dao.IssueDao
import com.turtlemint.assignment.data.db.entity.Issue

/**
 * IssueRepositoryImpl responsible for doing database and network transactions.
 */
class IssueRepositoryImpl(
    private val apiService: ApiService,
    private val issueDao: IssueDao
) : IssueRepository {

    companion object {
        private val TAG = IssueRepository::class.java.simpleName
    }

    override suspend fun fetchRemoteIssues(): Result<List<Issue>> {
        return try {
            val response = apiService.fetchIssues()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun fetchDataFromDB() = issueDao.fetchIssues()

    override suspend fun update(issue: Issue) = issueDao.update(issue)

    override suspend fun insert(data: List<Issue>) = issueDao.insert(data)
}