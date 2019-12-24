package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter.VideoItemAdapter
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class VideoFragment : Fragment(), ListFragment {
    override fun onPlayVideoOpen(index: Int) {
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.container, PlayVideoFragment.newInstance(index))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: VideoItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onEditNoteOpen(index:Int) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        (activity!! as MainActivity).showFab()
        (activity!! as MainActivity).showToolbar()
        recycler_view.layoutManager = LinearLayoutManager(this.activity!!.applicationContext)
        adapter = VideoItemAdapter(this.activity!!.applicationContext,this)
        viewModel.getDataItems().observe(this, object : Observer<List<ItemEntity>> {
            override fun onChanged(@Nullable words: List<ItemEntity>) {
                adapter.setItems(words)
            }
        })
        recycler_view.adapter = adapter
    }

}
