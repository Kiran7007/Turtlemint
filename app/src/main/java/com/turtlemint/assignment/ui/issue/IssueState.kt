package com.turtlemint.assignment.ui.issue

import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.ui.comment.CommentState

/**
 * IssueState holds the last state of the variable.
 */
sealed class IssueState {

    /**
     * Initial state of the view.
     */
    object Idle : IssueState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : IssueState()

    /**
     * Success result of the view.
     */
    data class Success(val list: List<Issue>) : IssueState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : IssueState()
}