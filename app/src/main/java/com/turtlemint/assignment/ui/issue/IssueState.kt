package com.turtlemint.assignment.ui.issue

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
     * Error state of the view.
     */
    data class Error(val message: String?) : IssueState()
}