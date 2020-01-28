package com.samplewp.repo

import com.newyorktimesdemo.model.NewyorkArticleResponse
import com.newyorktimesdemo.api.ApiClient
import com.newyorktimesdemo.api.OperationCallback
import retrofit2.Call
import retrofit2.Callback

open class ArticleRepository : DataSource {

    private var call: Call<NewyorkArticleResponse>? = null

    override fun getPopularArticle(callback: OperationCallback) {
        call = ApiClient.build()?.getPopularArticles("all-sections",1,"xwoS8ZGenXfukFBxl6cNnnYjLtezJGwa")


        call?.enqueue(object : Callback<NewyorkArticleResponse> {
            override fun onFailure(call: Call<NewyorkArticleResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<NewyorkArticleResponse>,
                response: retrofit2.Response<NewyorkArticleResponse>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Response error")
                    }
                }
            }
        })
    }


    override fun cancel() {
        call?.cancel()
    }
}