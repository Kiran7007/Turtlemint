package com.turtlemint.assignment.ui.issue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * IssueViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class IssueViewModel(private val repository: IssueRepository) : ViewModel() {

    companion object {
        private val TAG = IssueViewModel::class.java.simpleName
    }

    /**
     * Observes the intent.
     */
    val issueIntent = Channel<IssueIntent>(Channel.UNLIMITED)

    /**
     * Manage the states.
     */
    private val _state = MutableStateFlow<IssueState>(IssueState.Idle)
    val state: StateFlow<IssueState> get() = _state

    /**
     * Provides the data to the view in the form of live data.
     */
    val issues: LiveData<List<Issue>> get() = repository.fetchDataFromDB().asLiveData()

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            issueIntent.consumeAsFlow().collect {
                when (it) {
                    is IssueIntent.FetchRemoteIssue -> fetchDataFromRemote()
                    is IssueIntent.FetchLocalIssue -> fetchDataFromLocal()
                }
            }
        }
    }

    /**
     * Gets the data from the local database.
     */
    private fun fetchDataFromLocal() {
        if (issues.value.isNullOrEmpty()) {
            fetchDataFromRemote()
        }
    }

    /**
     * Gets the data from the remote server.
     */
    private fun fetchDataFromRemote() {
        viewModelScope.launch {
            try {
                _state.value = IssueState.Loading(true)
                when (val fetchIssues = repository.fetchRemoteIssues()) {
                    is Result.Success -> {
                        withContext(Dispatchers.IO) { repository.insert(fetchIssues.data) }
                    }
                    is Result.Error -> {
                        _state.value =
                            IssueState.Error("Please check the network connection and try again")
                    }
                }
                _state.value = IssueState.Loading(false)
            } catch (e: Exception) {
                Log.d(TAG, "Error while fetching the data")
                _state.value = IssueState.Loading(false)
            }
        }
    }
}