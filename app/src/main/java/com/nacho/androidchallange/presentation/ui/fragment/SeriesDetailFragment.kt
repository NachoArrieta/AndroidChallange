package com.nacho.androidchallange.presentation.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nacho.androidchallange.databinding.FragmentSeriesDetailBinding
import com.nacho.androidchallange.presentation.adapter.EpisodesAdapter
import com.nacho.androidchallange.presentation.viewmodel.SeriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private var _binding: FragmentSeriesDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SeriesViewModel by activityViewModels()
    private val args: SeriesDetailFragmentArgs by navArgs()
    private lateinit var episodeAdapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()

        viewModel.getEpisodes(args.serieId)

    }

    private fun setupUI() {
        binding.seriesDetailTitle.text = args.serieName
        binding.seriesDetailSummary.text = Html.fromHtml(args.serieSummary, Html.FROM_HTML_MODE_LEGACY)

        episodeAdapter = EpisodesAdapter(emptyList())
        binding.seriesDetailRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.seriesDetailRv.adapter = episodeAdapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.episodes.collectLatest { episodes ->
                episodeAdapter.updateList(episodes)
            }
        }
    }

}