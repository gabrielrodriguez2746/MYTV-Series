package com.mytv.home.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mytv.home.R
import com.mytv.home.models.TVSeriesWidgetModel
import com.mytv.home.viewModels.TVSeriesViewModel
import com.mytv.home.widget.TVSeriesHorizontalItem
import com.mytv.series.base.images.BaseImageLoader
import com.mytv.series.base.images.ImageConfigurationType
import com.mytv.series.base.listeners.OnFragmentInteraction
import com.mytv.series.base.ui.generics.BasePagedListAdapter
import com.mytv.series.base.ui.generics.RecyclerItem
import com.mytv.series.base.ui.generics.ViewHolder
import com.mytv.series.base.ui.generics.addRippleForeground
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TVSeriesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var imageLoader: BaseImageLoader

    lateinit var viewModel: TVSeriesViewModel

    private val itemsAdapter by lazy { TVSeriesAdapter() }

    private val displayMetrics by lazy {
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        metrics
    }
    private val itemHeight by lazy { (displayMetrics.heightPixels * 0.25).toInt() }
    private val imageWidth by lazy { (displayMetrics.widthPixels * 0.3).toInt() }
    private val imageConfiguration by lazy { ImageConfigurationType(1) }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TVSeriesViewModel::class.java)
        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer(itemsAdapter::submitList))
        activity?.lifecycle?.addObserver(viewModel)
    }

    private fun configureRecyclerView(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = itemsAdapter
    }

    inner class TVSeriesAdapter : BasePagedListAdapter<RecyclerItem<TVSeriesWidgetModel, String>>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
            return TVSeriesViewHolder(TVSeriesHorizontalItem(parent.context).apply {
                isClickable = true
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, itemHeight)
                addRippleForeground()
            })
        }
    }

    inner class TVSeriesViewHolder(private val holderItemView: TVSeriesHorizontalItem) :
        ViewHolder<TVSeriesWidgetModel, String>(holderItemView) {

        override fun bind(item: TVSeriesWidgetModel) {
            holderItemView.textViewName.text = item.name
            holderItemView.textViewOriginalName.text = item.originalName
            holderItemView.textViewPopularity.text = item.rate
            imageLoader.loadImageInto(
                item.image,
                holderItemView.imageViewSeries,
                itemHeight,
                imageWidth,
                imageConfiguration
            )
            holderItemView.setOnClickListener {
                // TODO Add different listener for favorites
                (activity as OnFragmentInteraction).onItemClicked(FRAGMENT_NAME, item.id.toString())
            }
        }

        override fun updateBind(item: String) = Unit

    }

    companion object {
        const val FRAGMENT_NAME = "tv_series"
    }

}