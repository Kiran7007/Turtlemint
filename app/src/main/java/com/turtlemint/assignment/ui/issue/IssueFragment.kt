package com.turtlemint.assignment.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.turtlemint.assignment.adapters.IssueAdapter
import com.turtlemint.assignment.databinding.IssueFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * IssueFragment shows the people list.
 */
class IssueFragment : Fragment() {

    /**
     * IssueViewModel injected bu dependency injection.
     */
    private val viewModel by viewModel<IssueViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: IssueFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: IssueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssueFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerState()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        adapter = IssueAdapter(viewModel) {
            if (!it.commentsUrl.isNullOrEmpty()) {
                val direction =
                    IssueFragmentDirections.actionIssueFragmentToCommentFragment(it.commentsUrl, it.title!!)
                findNavController().navigate(direction)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is IssueState.Idle -> {
                        viewModel.issueIntent.send(IssueIntent.FetchLocalIssue)
                    }
                    is IssueState.Loading -> {
                        if (it.isLoading) {
                            setViewVisibility(View.GONE, View.GONE, View.VISIBLE)
                        } else {
                            setViewVisibility(View.VISIBLE, View.GONE, View.GONE)
                        }
                    }
                    is IssueState.Success -> {
                        adapter.submitList(it.list)
                    }
                    is IssueState.Error -> {
                        setViewVisibility(View.GONE, View.VISIBLE, View.GONE)
                        it.message?.let { message -> shoToast(message) }
                    }
                }
            }
        }
    }

    /**
     * Update view visibility.
     */
    private fun setViewVisibility(recyclerView: Int, emptyText: Int, progressBar: Int) {
        binding.recyclerView.visibility = recyclerView
        binding.tvEmpty.visibility = emptyText
        binding.progressCircular.visibility = progressBar
    }

    /**
     * Shows message.
     */
    private fun shoToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}