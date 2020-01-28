package com.newyorktimesdemo.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.newyorktimesdemo.R
import com.newyorktimesdemo.base.BaseActivity
import com.newyorktimesdemo.databinding.DetailsLayoutBinding
import com.newyorktimesdemo.model.Results


class ArticleDetailsActivity : BaseActivity<ViewDataBinding>() {
    override fun getContentView(): Int = R.layout.details_layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind: DetailsLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.details_layout)
        val news: Results = intent.getSerializableExtra("news") as Results
        bind.news = news
        Glide.with(this).load(news?.media?.get(0)?.mediametadata?.get(0)?.url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(bind.logo)
    }
}