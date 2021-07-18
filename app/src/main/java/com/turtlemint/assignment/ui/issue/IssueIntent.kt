package com.turtlemint.assignment.ui.issue

/**
 * IssueIntent triggers the event to the viewModel.
 */
sealed class IssueIntent {

    /**
     * Trigger the intent to fetch the data from remote server.
     */
    object FetchRemoteIssue : IssueIntent()

    /**
     * Trigger the intent to fetch the data from local database.
     */
    object FetchLocalIssue : IssueIntent()
}