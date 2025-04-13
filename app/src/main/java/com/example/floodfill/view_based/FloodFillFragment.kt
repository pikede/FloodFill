package com.example.floodfill.view_based

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.floodfill.databinding.FragmentItemBinding
import com.example.floodfill.view_based.FloodFillViewModel.Companion.GRID_CELLS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloodFillFragment : Fragment() {
    private var _binding: FragmentItemBinding? = null
    val binding get() = _binding!!
    private lateinit var gridItemAdapter: GridItemAdapter
    val viewModel: FloodFillViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() =
            FloodFillFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.gridState.collectLatest {
                        gridItemAdapter.updateGridDataset(it)
                    }
                }

                launch {
                    viewModel.loadingState.collectLatest {
                        binding.loadingProgress.visibility = if (it) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    private fun setupUI() {
        gridItemAdapter = GridItemAdapter() { position ->
            val row = position / GRID_CELLS
            val col = position % GRID_CELLS
            viewModel.fillOnce(row, col)
        }
        binding.grid.run {
            layoutManager = GridLayoutManager(requireContext(), GRID_CELLS)
            adapter = gridItemAdapter
        }
        binding.red.run {
            val color = android.graphics.Color.RED
            setBackgroundColor(color)
            setOnClickListener { viewModel.changeFillColor(color) }
        }
        binding.blue.run {
            val color = android.graphics.Color.BLUE
            setBackgroundColor(color)
            setOnClickListener { viewModel.changeFillColor(color) }
        }
        binding.green.run {
            val color = android.graphics.Color.GREEN
            setBackgroundColor(color)
            setOnClickListener { viewModel.changeFillColor(color) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
