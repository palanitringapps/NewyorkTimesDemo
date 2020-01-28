package com.newyorktimesdemo.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.newyorktimesdemo.api.OperationCallback
import com.newyorktimesdemo.model.NewyorkArticleResponse
import com.newyorktimesdemo.model.Results
import com.samplewp.repo.ArticleRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class ArticleViewModelTest {

    @Mock
    private lateinit var repository: ArticleRepository
    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback>

    private lateinit var viewModel: ArticleViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var sampleResponseObserver: Observer<NewyorkArticleResponse>
    private lateinit var row: ArrayList<Results>

    private lateinit var feedEmptyList: NewyorkArticleResponse
    private lateinit var feedList: NewyorkArticleResponse

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)

        viewModel = ArticleViewModel(context, repository)
        feedEmptyList = NewyorkArticleResponse("success", "copy", 5, ArrayList())

        mockData()
        setupObservers()
    }

    @Test
    fun ListRepositoryAndViewModel() {

        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            feedResponse.observeForever(sampleResponseObserver)
        }
        viewModel.getNewsFeeds(false)
        verify(repository, times(1)).getPopularArticle(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(feedList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertEquals(viewModel.feedResponse.value?.results?.size, 3)
    }

    @Test
    fun FailRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        viewModel.getNewsFeeds(false)
        verify(repository, times(1)).getPopularArticle(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Response error")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.onMessageError.value.toString().equals("Response error"))
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        sampleResponseObserver = mock(Observer::class.java) as Observer<NewyorkArticleResponse>
    }


    private fun mockData() {

        row = ArrayList()

        row.add(
            Results(
                "https://www.nytimes.com/2020/01/26/sports/basketball/kobe-bryant-dead.html",
                "Bryant, Kobe;Basketball;Deaths (Fatalities);Helicopters;Los Angeles Lakers",
                null,
                "Sports",
                "By ALAN BLINDER, KEVIN DRAPER and ELENA BERGERON",
                "Article",
                "N.B.A. Star Kobe Bryant Dies in California Helicopter Crash",
                "The retired N.B.A. star, 41, and his daughter Gianna were among nine people in a helicopter that crashed near Calabasas, Calif. A college baseball coach and his wife and daughter were also killed.",
                "2020-01-26",
                "The New York Times",
                100000006942920,
                100000006942920,
                2,
                null
            )
        )
        row.add(
            Results(
                "https://www.nytimes.com/2020/01/26/sports/basketball/kobe-bryant-dead.html",
                "Bryant, Kobe;Basketball;Deaths (Fatalities);Helicopters;Los Angeles Lakers",
                null,
                "Sports",
                "By ALAN BLINDER, KEVIN DRAPER and ELENA BERGERON",
                "Article",
                "N.B.A. Star Kobe Bryant Dies in California Helicopter Crash",
                "The retired N.B.A. star, 41, and his daughter Gianna were among nine people in a helicopter that crashed near Calabasas, Calif. A college baseball coach and his wife and daughter were also killed.",
                "2020-01-26",
                "The New York Times",
                100000006942920,
                100000006942920,
                2,
                null
            )
        )
        row.add(
            Results(
                "https://www.nytimes.com/2020/01/26/sports/basketball/kobe-bryant-dead.html",
                "Bryant, Kobe;Basketball;Deaths (Fatalities);Helicopters;Los Angeles Lakers",
                null,
                "Sports",
                "By ALAN BLINDER, KEVIN DRAPER and ELENA BERGERON",
                "Article",
                "N.B.A. Star Kobe Bryant Dies in California Helicopter Crash",
                "The retired N.B.A. star, 41, and his daughter Gianna were among nine people in a helicopter that crashed near Calabasas, Calif. A college baseball coach and his wife and daughter were also killed.",
                "2020-01-26",
                "The New York Times",
                100000006942920,
                100000006942920,
                2,
                null
            )
        )
        feedList = NewyorkArticleResponse("success", "copy", 3, row)
    }

}