package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter.NewsItemAdapter
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter.NoteItemAdapter
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter.RecentItemAdapter
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.NewsEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.tools.NewsHttpRequest
import kotlinx.android.synthetic.main.fragment_song_news.*
import kotlinx.android.synthetic.main.main_fragment.recycler_view
import org.json.JSONObject


class SongNewsFragment : Fragment() {

    companion object {
        fun newInstance() = SongNewsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NewsItemAdapter
    private var items:ArrayList<NewsEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_song_news, container, false)
    }

    fun onGetResult(result:JSONObject){
        loading.visibility=View.GONE
        var items = result.getJSONArray("articles")
        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            val url = item["url"].toString()
            val title = item["title"].toString()
            this.items.add(NewsEntity(url,title))
            Log.v("ASD",title)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        (activity!! as MainActivity).hideFab()
        (activity!! as MainActivity).showToolbar()

        val apiKey = (activity as MainActivity).apiKeyFromJNI()

        val myUrl = "https://newsapi.org/v2/top-headlines?country=us&category=music&sortBy=publishedAt&apiKey=$apiKey"
        val getRequest = NewsHttpRequest(this)
        getRequest.execute(myUrl)


        recycler_view.layoutManager = LinearLayoutManager(this.activity!!.applicationContext)
        adapter = NewsItemAdapter(this.activity!!.applicationContext,items)
        recycler_view.adapter = adapter

    }

}
