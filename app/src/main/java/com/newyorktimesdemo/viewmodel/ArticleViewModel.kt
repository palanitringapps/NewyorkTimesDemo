package com.newyorktimesdemo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newyorktimesdemo.model.NewyorkArticleResponse
import com.newyorktimesdemo.model.Results
import com.newyorktimesdemo.api.OperationCallback
import com.newyorktimesdemo.base.BaseViewModel
import com.samplewp.repo.ArticleRepository

open class ArticleViewModel(app: Application, private val repository: ArticleRepository) : BaseViewModel(app) {


    var _feedsModel = MutableLiveData<NewyorkArticleResponse>().apply {
        value = NewyorkArticleResponse("success","copy",5,ArrayList<Results>())
    }

    var feedResponse: LiveData<NewyorkArticleResponse> = _feedsModel

    val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()

    fun getNewsFeeds(isPulltoRefresh: Boolean) {


        _isViewLoading.postValue(!isPulltoRefresh)

        repository.getPopularArticle(object : OperationCallback {
            override fun onSuccess(obj: Any?) {

                _isViewLoading.postValue(false)

                if (obj != null && obj is NewyorkArticleResponse) _feedsModel.value = obj
                else {
                    _isEmptyList.postValue(true)
                }
            }

            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(obj)
            }

        })
    }

    class FeedsViewModelFactory(private var app: Application, private var repo: ArticleRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java, ArticleRepository::class.java)
                .newInstance(app, repo)
        }
    }
}