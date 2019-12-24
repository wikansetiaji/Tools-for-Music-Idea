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
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.ListFragment
import kotlinx.android.synthetic.main.recent_audio_item.view.*
import kotlinx.android.synthetic.main.recent_text_item.view.text

class AudioItemAdapter(val context: Context, val fragment: ListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items : ArrayList<ItemEntity> = arrayListOf()

    fun setItems(items:List<ItemEntity>){
        this.items = ArrayList(items)
        this.itemsShown = ArrayList(items.filter {
            it.getType() == 1
        })
        notifyDataSetChanged()
    }

    var itemsShown:ArrayList<ItemEntity>

    init {
        itemsShown = ArrayList(items.filter {
            it.getType() == 1
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AudioViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_audio_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsShown.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AudioViewHolder).bindView(position)
    }

    override fun getItemViewType(position: Int): Int {
        return itemsShown.get(position).getType()
    }

    inner class AudioViewHolder (var view: View) : RecyclerView.ViewHolder(view) {
        val text = view.text
        val play = view.play
        val time = view.time

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@AudioItemAdapter.itemsShown.get(position) as AudioEntity
            text.text = item.name
            play.setOnClickListener {
                play.setOnClickListener {
                    (fragment.getActivity()!! as MainActivity).onPlayAudioOpen(items.indexOf(itemsShown[position]))
                }
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
}