package com.app.nytimesdemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.nytimesdemo.ITEM_JSON
import com.app.nytimesdemo.R
import com.app.nytimesdemo.data.enums.Period
import com.app.nytimesdemo.data.models.NewsResult
import com.app.nytimesdemo.data.network.repository.NewsRepository
import com.app.nytimesdemo.ui.adapter.NewsRecyclerViewAdapter
import com.app.nytimesdemo.viewmodel.INewsImplementer
import com.app.nytimesdemo.viewmodel.NewsViewModel
import com.google.gson.Gson

class NewItemListActivity : AppCompatActivity(), INewsImplementer, NewsRecyclerViewAdapter.OnItemClickListener {

    /**
     * Initializing View model by lazy loading
     */
    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(this@NewItemListActivity).get(NewsViewModel::class.java)
    }

    private var progressView: CardView? = null
    private var newsRecyclerViewAdapter: NewsRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Providing Repository and Adding Observer and Listener or callback with view model */
        newsViewModel.provideRepository(NewsRepository())
        newsViewModel.listener = this@NewItemListActivity

        initToolbar()
        initViews()
        initNewsRecyclerView()

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //Fetch News List.
        newsViewModel.fetchNews(Period.WEEK)
    }

    private fun initToolbar() {
        val toolbar = findViewById<View?>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title
    }

    private fun initViews() {
        progressView = findViewById(R.id.loading_layout)
    }

    private fun initNewsRecyclerView() {
        val recyclerView = findViewById<RecyclerView?>(R.id.item_list)

        newsRecyclerViewAdapter = NewsRecyclerViewAdapter(this)
        recyclerView?.adapter = newsRecyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater? = menuInflater
        menuInflater?.inflate(R.menu.menu_new_item_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val period = when (item.itemId) {
            R.id.menu_one_day -> Period.DAY
            R.id.menu_seven_days -> Period.WEEK
            R.id.menu_thirty_days -> Period.MONTH
            else -> null
        } ?: return false

        //set checked the current selected menu item.
        item.isChecked = true

        //Fetch Selected Period News List.
        newsViewModel.fetchNews(period)
        return true
    }

    override fun onItemClickListener(item: NewsResult) {
        val json = Gson().toJson(item)
        val intent = Intent(this@NewItemListActivity, NewsItemDetailActivity::class.java)
        intent.putExtra(ITEM_JSON, json)
        startActivity(intent)
    }

    override fun onStartLoading() {
        progressView?.visibility = View.VISIBLE
    }

    override fun onStopLoading() {
        progressView?.visibility = View.GONE
    }

    override fun onError(error: String) {
    }

    override fun onNewsFeedResponse(newsResults: MutableList<NewsResult>?) {
        newsResults?.let { newsRecyclerViewAdapter?.setItemList(it) }
    }


}