package com.project.communicationhub.news

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.MainActivity
import com.project.communicationhub.R
import com.project.communicationhub.databinding.ActivityNewsReadingBinding
import kotlinx.android.synthetic.main.activity_news_reading.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsReadingActivity : AppCompatActivity(), CategoryAdapter.CategoryClickInterface {

    // Initialising Variables
    private lateinit var newsReadingActivity: ActivityNewsReadingBinding
    private lateinit var articlesArrayList: ArrayList<Articles>
    private lateinit var categoryModelArrayList: ArrayList<CategoryModel>
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsReadingActivity = ActivityNewsReadingBinding.inflate(layoutInflater)
        setContentView(newsReadingActivity.root)

        setSupportActionBar(newsReadingActivity.toolbarNews)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }

        articlesArrayList = ArrayList()
        categoryModelArrayList = ArrayList()
        newsAdapter = NewsAdapter(articlesArrayList, this)
        categoryAdapter = CategoryAdapter(categoryModelArrayList, this
            , object : CategoryAdapter.CategoryClickInterface{
                override fun onCategoryClick(position: Int) {
                    val category = categoryModelArrayList[position].category
                    getNews(category)
                }
            }/*this@NewsReadingActivity as CategoryAdapter.CategoryClickInterface*/)
        //newsReadingActivity.news.layoutManager = LinearLayoutManager(this)
        newsReadingActivity.news.adapter = newsAdapter
        //newsReadingActivity.categories.layoutManager = LinearLayoutManager(this)
        newsReadingActivity.categories.adapter = categoryAdapter
        getCategories()
        getNews("All")
        newsReadingActivity.news.adapter = newsAdapter
        //newsAdapter.notifyDataSetChanged()

    }

    private fun getCategories(){
        categoryModelArrayList.add(CategoryModel("All"
            ,"https://media.istockphoto.com/photos/colorful-abstract-collage-from-clippings-with-letters-and-numbers-picture-id1278583120?s=612x612"))
        categoryModelArrayList.add(CategoryModel("Technology"
            ,"https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2072&q=80"))
        categoryModelArrayList.add(CategoryModel("Science"
            ,"https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1172&q=80"))
        categoryModelArrayList.add(CategoryModel("Sports"
            ,"https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="))
        categoryModelArrayList.add(CategoryModel("General"
            ,"https://images.unsplash.com/photo-1557822477-ec8d8bbbcd2a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fGdlbmVyYWwlMjBuZXdzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"))
        categoryModelArrayList.add(CategoryModel("Business"
            ,"https://media.istockphoto.com/photos/click-here-and-it-should-direct-you-further-picture-id1209869264?b=1&k=20&m=1209869264&s=170667a&w=0&h=dGbPJ8Beo5oTkIEg6Yxh8bX057_lkx4L7qqTbs7dzsA="))
        categoryModelArrayList.add(CategoryModel("Entertainment"
            ,"https://media.istockphoto.com/photos/the-musicians-were-playing-rock-music-on-stage-there-was-an-audience-picture-id1319479588?b=1&k=20&m=1319479588&s=170667a&w=0&h=bunblYyTDA_vnXu-nY4x4oa7ke6aiiZKntZ5mfr-4aM="))
        categoryModelArrayList.add(CategoryModel("Health"
            ,"https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aGVhbHRofGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"))
        newsReadingActivity.categories.adapter = categoryAdapter
        //categoryAdapter.notifyDataSetChanged()
    }

    private fun getNews(category: String){
        newsReadingActivity.progressBarNews.visibility = View.VISIBLE
        articlesArrayList.clear()
        val categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category=$category&apiKey=62c30cc679014b5aa38c950005614e88"
        //https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=published&language=en&apiKey=62c30cc679014b5aa38c950005614e88
        val url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=published&language=en&apiKey=62c30cc679014b5aa38c950005614e88"
        val BASE_URL = "https://newsapi.org/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call: Call<NewsModel> = if(category == "All"){
            retrofitAPI.getAllNews(url)
        } else {
            retrofitAPI.getAllNews(categoryUrl)
        }

        doAsync {
            call.enqueue(object : Callback<NewsModel> {
                override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>){
                    val newsModel: NewsModel? = response.body()
                    newsReadingActivity.progressBarNews.visibility = View.GONE
                    val articles: ArrayList<Articles> = newsModel!!.articles
                    for (i in 0 until articles.size){
                        articlesArrayList.add(
                            Articles(
                                articles[i].title,
                                articles[i].description,
                                articles[i].urlToImage,
                                articles[i].url,
                                articles[i].content))
                    }
                    newsReadingActivity.news.adapter = newsAdapter
                    //newsAdapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<NewsModel>?, t: Throwable?){
                    Toast.makeText(this@NewsReadingActivity, "Failed to load News!!", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home  -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        startActivity(
            Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun onCategoryClick(position: Int) {
        val category = categoryModelArrayList[position].category
        getNews(category)
    }

}