package com.app.nytimesdemo.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.app.nytimesdemo.ITEM_JSON
import com.app.nytimesdemo.R
import com.app.nytimesdemo.data.models.NewsResult
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.*

class NewsItemDetailActivity : AppCompatActivity() {

    private lateinit var ivNewsBanner: ImageView
    private lateinit var tvNewsTitle: TextView
    private lateinit var tvNewsAbstract: TextView
    private lateinit var tvNewsMetaData: TextView

    private var mItem: NewsResult? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        //Init Toolbar.
        initToolbar()
        initViews()
        //Fetch Item Details from Intent
        mItem = fetchItemDetailsFromIntent()

        //If Item Detail is not null or available then apply details to views.
        mItem?.let { applyItemDetails(it) }

    }

    private fun initToolbar() {
        val toolbar = findViewById<View?>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        ivNewsBanner = findViewById(R.id.iv_newsBanner)
        tvNewsTitle = findViewById(R.id.tv_newsTitle)
        tvNewsAbstract = findViewById(R.id.tv_newsAbstract)
        tvNewsMetaData = findViewById(R.id.tv_newsMetaData)
    }

    private fun fetchItemDetailsFromIntent(): NewsResult? {
        var item: NewsResult? = null
        val argument = intent.getStringExtra(ITEM_JSON)
        argument?.let {
            item = Gson().fromJson(argument, NewsResult::class.java)
        }

        return item
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyItemDetails(item: NewsResult) {
        if (item.media.isNullOrEmpty().not()) {
            //Fetching the available high resolution image
            val mediaList = item.media?.get(0)
            val mediaMetadata = mediaList?.mediaMetadata
            val highestQualityImageIndex = mediaMetadata?.size?.minus(1)
            highestQualityImageIndex?.let {
                if (highestQualityImageIndex > 0) {
                    //Set Image
                    Picasso.get()
                        .load(mediaMetadata[highestQualityImageIndex]?.url)
                        .placeholder(R.drawable.progress_animation)
                        .fit()
                        .into(ivNewsBanner)
                }
            }
        }

        //Fetching News Meta data and adding altogether
        val newsMetaDataList = ArrayList<String?>()
        if (item.section.isNullOrEmpty().not()) newsMetaDataList.add(item.section)
        if (item.subsection.isNullOrEmpty().not()) newsMetaDataList.add(item.subsection)
        if (item.publishedDate.isNullOrEmpty().not()) newsMetaDataList.add(item.publishedDate)
        // Set Metadata
        tvNewsMetaData.text = TextUtils.join(" | ", newsMetaDataList)

        //Set Title
        tvNewsTitle.text = item.title
        //Set Content
        tvNewsAbstract.text = item.resultAbstract
    }

}