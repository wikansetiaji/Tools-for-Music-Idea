package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_note.toolbar
import kotlinx.android.synthetic.main.fragment_play_video.*

class PlayVideoFragment(var index: Int) : Fragment() {

    companion object {
        fun newInstance(index: Int) = PlayVideoFragment(index)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_play_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        val item = viewModel.items.value!![index] as VideoEntity
        (activity!! as MainActivity).hideFab()
        (activity!! as MainActivity).hideToolbar()
        toolbar.setNavigationOnClickListener {
            this.activity!!.onBackPressed()
        }
        videoView.setVideoURI(Uri.parse(item.uri))
        videoView.start()
        videoView.setOnCompletionListener {
            play.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        }
        play.setOnClickListener {
            if (videoView.isPlaying){
                play.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                videoView.pause()
            }
            else{
                play.setImageResource(R.drawable.ic_pause_black_24dp)
                videoView.start()
            }
        }
    }

}
