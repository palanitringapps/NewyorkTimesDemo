package com.newyorktimesdemo.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.newyorktimesdemo.R
import com.newyorktimesdemo.base.BaseActivity
import com.samplewp.repo.ArticleRepository
import com.newyorktimesdemo.ui.adapter.FeedAdapter
import com.newyorktimesdemo.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ViewDataBinding>(), RecyclerViewClickListener {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var feedAdapter: FeedAdapter

    override fun itemClicked(position: Int) {
        startActivity(Intent(this, ArticleDetailsActivity::class.java).apply {
            putExtra(
                "news",
                viewModel._feedsModel.value!!.results[position]
            )
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

        viewModel.getNewsFeeds(false)

        setAdapter()
        viewModel._feedsModel.observe(this, Observer {
            setFeedsData()
        })


        viewModel._isViewLoading.observe(this, Observer {
            when (it) {
                true -> showProgress()
                false -> hideProgress()
            }
        })

        viewModel._onMessageError.observe(
            this,
            Observer {
                showSnackBar("Network error please try again")
                sr_pull.isRefreshing = false
            })
        sr_pull.setOnRefreshListener {
            sr_pull.isRefreshing = true
            viewModel.getNewsFeeds(true)
        }
    }

    override fun getContentView(): Int = R.layout.activity_main


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ArticleViewModel.FeedsViewModelFactory(application, ArticleRepository())
        ).get(ArticleViewModel::class.java)
    }


    private fun setFeedsData() {
        sr_pull.isRefreshing = false
        feedAdapter.addAll(viewModel.feedResponse.value!!.results)
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter(this)
        rv_feed_list.layoutManager = LinearLayoutManager(this)
        rv_feed_list.adapter = feedAdapter
    }
}