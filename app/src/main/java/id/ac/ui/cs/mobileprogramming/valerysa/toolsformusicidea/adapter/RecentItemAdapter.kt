package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter

import android.content.Context
import android.media.MediaPlayer
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.ListFragment
import kotlinx.android.synthetic.main.recent_audio_item.view.*
import kotlinx.android.synthetic.main.recent_text_item.view.*
import kotlinx.android.synthetic.main.recent_text_item.view.text

class RecentItemAdapter(val context: Context, val fragment:ListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items : ArrayList<ItemEntity> = arrayListOf()

    fun setItems(items:List<ItemEntity>){
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==0){
            return TextViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_text_item, parent, false))
        }
        else if (viewType==1){
            return AudioViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_audio_item, parent, false))
        }
        else{
            return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_video_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> (holder as TextViewHolder).bindView(position)
            1 -> (holder as AudioViewHolder).bindView(position)
            2 -> (holder as VideoViewHolder).bindView(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items.get(position).getType()
    }

    inner class TextViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val edit = view.edit
        val text = view.text

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@RecentItemAdapter.items.get(position) as TextEntity
            text.text = item.text
            edit.setOnClickListener {
                fragment.onEditNoteOpen(position)
            }
        }
    }

    inner class AudioViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val play = view.play
        val text = view.text
        val time = view.time

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@RecentItemAdapter.items.get(position) as AudioEntity
            text.text = item.name
            play.setOnClickListener {
                (fragment.getActivity()!! as MainActivity).onPlayAudioOpen(position)
            }
            val mediaPlayer = MediaPlayer()

            try {
                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().absolutePath + "/${item.name}")
                mediaPlayer.prepare()
                val duration:Int = mediaPlayer.duration
                val second:Int = (duration/1000)%60
                val minute:Int = (duration/60000)%60
                val hour:Int = (duration/3600000)
                time.text = "${hour}:${minute}:${second}"
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    inner class VideoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val play = view.play
        val text = view.text

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@RecentItemAdapter.items.get(position) as VideoEntity
            text.text = item.name
            play.setOnClickListener {
                fragment.onPlayVideoOpen(position)
            }
        }
    }
}
