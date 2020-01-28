package com.samplewp.repo

import com.newyorktimesdemo.api.OperationCallback

interface DataSource {
    fun getPopularArticle(callback: OperationCallback)
    fun cancel()
}