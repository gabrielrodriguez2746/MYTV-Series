package com.mytv.home.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mytv.data.models.TVSeries
import com.mytv.home.R
import com.mytv.home.viewModels.TVSeriesViewModel
import com.mytv.series.base.ui.generics.*
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TVSeriesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: TVSeriesViewModel

    private val itemsAdapter by lazy { TVSeriesAdapter() }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvseries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView(view.findViewById(R.id.rvTVSeries))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TVSeriesViewModel::class.java)
        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer(itemsAdapter::submitList))
        activity?.lifecycle?.addObserver(viewModel)
    }

    private fun configureRecyclerView(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = itemsAdapter
    }

    inner class TVSeriesAdapter : BasePagedListAdapter<RecyclerItem<TVSeries, String>>() {
        override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return TVSeriesViewHolder(TextView(parent.context).apply { layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT) })
        }

    }
    inner class TVSeriesViewHolder(private val textView: TextView) : ViewHolder(textView), Binder<TVSeries, String> {
        override fun bind(item: TVSeries) {
            textView.text = item.originalName
        }

        override fun updateBind(item: String) = Unit

    }

}