package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.ListFragment
import kotlinx.android.synthetic.main.recent_audio_item.view.*
import kotlinx.android.synthetic.main.recent_text_item.view.*
import kotlinx.android.synthetic.main.recent_text_item.view.text


class VideoItemAdapter(val context: Context, val fragment: ListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items : ArrayList<ItemEntity> = arrayListOf()

    fun setItems(items:List<ItemEntity>){
        this.items = ArrayList(items)
        this.itemsShown = ArrayList(items.filter {
            it.getType() == 2
        })
        notifyDataSetChanged()
    }

    var itemsShown:ArrayList<ItemEntity>

    init {
        itemsShown = ArrayList(items.filter {
            it.getType() == 2
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_video_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsShown.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoViewHolder).bindView(position)
    }

    override fun getItemViewType(position: Int): Int {
        return itemsShown.get(position).getType()
    }

    inner class VideoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val play = view.play
        val text = view.text

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@VideoItemAdapter.itemsShown.get(position) as VideoEntity
            text.text = item.name
            play.setOnClickListener {
                play.setOnClickListener {
                    fragment.onPlayVideoOpen(items.indexOf(itemsShown[position]))
                }
            }
        }
    }
}
