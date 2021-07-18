package com.turtlemint.assignment.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.turtlemint.assignment.adapters.CommentAdapter
import com.turtlemint.assignment.databinding.CommentFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * CommentFragment shows the people list.
 */
class CommentFragment : Fragment() {

    /**
     * CommentViewModel injected bu dependency injection.
     */
    private val viewModel by viewModel<CommentViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: CommentFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CommentFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerIssues()
        observerState()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        adapter = CommentAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Observes the peoples data and set to the recycler view.
     */
    private fun observerIssues() {
        viewModel.comments.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.tvEmpty.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is CommentState.Idle -> {
                        val commentUrl =
                            CommentFragmentArgs.fromBundle(requireArguments()).commentUrl
                        viewModel.commentIntent.send(CommentIntent.FetchComment(commentUrl))
                    }
                    is CommentState.Loading -> {
                    }
                    is CommentState.Error -> {
                        it.message?.let { message -> shoToast(message) }
                    }
                }
            }
        }
    }

    /**
     * Shows message.
     */
    private fun shoToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}