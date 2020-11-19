package com.app.nytimesdemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.nytimesdemo.data.enums.Period
import com.app.nytimesdemo.data.network.repository.FakeNewsRepositoryTest
import com.app.nytimesdemo.data.network.repository.IRepository
import org.junit.*
import org.junit.rules.TestRule

class NewsViewModelTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val newsRepository: IRepository by lazy { FakeNewsRepositoryTest() }


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }


    @Test
    fun `fetchNews return Success`() {
        if (newsRepository is FakeNewsRepositoryTest) (newsRepository as FakeNewsRepositoryTest).responseType = FakeNewsRepositoryTest.Response.SUCCESS
        val response = newsRepository.fetchNews(Period.WEEK.getPeriod())
            .subscribe({ response ->
                Assert.assertNotNull(response)
            }, { error ->
                Assert.assertNull(error)
            })
    }

    @Test
    fun `fetchNews return Error`() {
        if (newsRepository is FakeNewsRepositoryTest) (newsRepository as FakeNewsRepositoryTest).responseType = FakeNewsRepositoryTest.Response.ERROR
        val response = newsRepository.fetchNews(-1) //Invalid value..
            .subscribe({ response ->
            }, { error ->
                Assert.assertNotNull(error)
            })
    }


}