package com.nacho.androidchallange.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nacho.androidchallange.databinding.FragmentSeriesListBinding
import com.nacho.androidchallange.presentation.adapter.SeriesAdapter
import com.nacho.androidchallange.presentation.viewmodel.SeriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    private var _binding: FragmentSeriesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SeriesViewModel by activityViewModels()
    private lateinit var adapter: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()

    }

    private fun setupUI() {
        adapter = SeriesAdapter(emptyList()) { serie ->
            val action = SeriesListFragmentDirections
                .actionSeriesListFragmentToSeriesDetailFragment(
                    serie.id,
                    serie.name ?: "",
                    serie.summary ?: "",
                    serie.image?.original ?: ""
                )
            findNavController().navigate(action)
        }
        binding.seriesListRv.layoutManager = LinearLayoutManager(requireContext())
        binding.seriesListRv.adapter = adapter

        binding.seriesListTie.doAfterTextChanged { text ->
            viewModel.searchSeries(text.toString())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seriesList.collectLatest { series ->
                adapter.updateList(series)
                binding.seriesListRv.isVisible = series.isNotEmpty() && !viewModel.isLoading.value
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.seriesListPb.isVisible = isLoading
                binding.seriesListRv.isVisible =
                    !isLoading && viewModel.seriesList.value.isNotEmpty()
            }
        }
    }

}