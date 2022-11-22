package com.victormartin.archmvvm.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victormartin.archmvvm.databinding.FragmentListBinding
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.ui.view.HubActivity
import com.victormartin.archmvvm.ui.view.adapter.LinearRecyclerViewAdapter
import com.victormartin.archmvvm.ui.view.listener.ItemClickListener
import com.victormartin.archmvvm.ui.view.adapter.RecyclerViewLoadMoreScroll
import com.victormartin.archmvvm.ui.view.listener.OnLoadMoreListener
import com.victormartin.archmvvm.ui.viewmodel.DataViewModel

class ListItemsFragment : Fragment(), ItemClickListener {

    private lateinit var binding: FragmentListBinding

    val dataViewModel: DataViewModel by activityViewModels()

    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var adapter: LinearRecyclerViewAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    private var columnCount = 1

    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)


        dataViewModel.isLoadedMoreData.observe(viewLifecycleOwner, Observer { isLoadedMoreData ->
            if(isLoadedMoreData){
                page += 1
                //Remove the Loading View
                adapter.removeLoadingView()
                //We adding the data to our main ArrayList
                dataViewModel.playersModel.value?.let { adapter.addData(it.data) }
                //Change the boolean isLoading to false
                scrollListener.setLoaded()
                //Update the recyclerView in the main thread
                binding.rvListPlayers.post {
                    adapter.notifyDataSetChanged()
                }
            }
        })

        layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        adapter = LinearRecyclerViewAdapter(dataViewModel.playersModel.value?.data ?: mutableListOf(), dataViewModel.eastIcon, dataViewModel.westIcon, this@ListItemsFragment)
        (adapter as LinearRecyclerViewAdapter).notifyDataSetChanged()
        binding.rvListPlayers.adapter = adapter
        binding.rvListPlayers.setHasFixedSize(true)

        scrollListener = RecyclerViewLoadMoreScroll(layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        binding.rvListPlayers.addOnScrollListener(scrollListener)

        return binding.root
    }

    // On Item click on the recyclerview
    override fun onItemClick(playerPositionOnList: Int, data: DataDomainModel) {

        dataViewModel.latestSelectedPlayer.value = data
        (activity as HubActivity).navigateToDetailPlayer()

    }


    private fun loadMoreData() {
        //Add the Loading View
        adapter.addLoadingView()

        dataViewModel.getAllPlayers(page)
    }
}