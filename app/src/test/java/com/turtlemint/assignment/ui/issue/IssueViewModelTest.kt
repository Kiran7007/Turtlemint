package com.turtlemint.assignment.ui.issue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.data.repository.IssueRepository
import com.turtlemint.assignment.util.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class IssueViewModelTest : TestCase() {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IssueRepository

    @Mock
    private lateinit var observer: Observer<List<Issue>>

    @Before
    fun setup() {
    }

    @Test
    fun testRequestIssues_whenFetchDataFromRemoteORLocalDatabase_thenCheckIssueList() {
        testCoroutineRule.runBlockingTest {
            doReturn(flowOf { emptyList<Issue>() })
                .`when`(repository)
                .fetchDataFromDB()

            val viewModel = IssueViewModel(repository)
            viewModel.issues.observeForever(observer)
            viewModel.issueIntent.send(IssueIntent.FetchLocalIssue)
            verify(repository).fetchRemoteIssues()
            viewModel.issues.removeObserver(observer)
        }
    }
}