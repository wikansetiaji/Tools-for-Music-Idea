package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.ListFragment
import kotlinx.android.synthetic.main.recent_text_item.view.*



class NoteItemAdapter(val context: Context, val fragment: ListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items : ArrayList<ItemEntity> = arrayListOf()

    fun setItems(items:List<ItemEntity>){
        this.items = ArrayList(items)
        this.itemsShown = ArrayList(items.filter {
            it.getType() == 0
        })
        notifyDataSetChanged()
    }

    var itemsShown:ArrayList<ItemEntity>

    init {
        itemsShown = ArrayList(items.filter {
            it.getType() == 0
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TextViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsShown.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TextViewHolder).bindView(position)
    }

    override fun getItemViewType(position: Int): Int {
        return itemsShown.get(position).getType()
    }

    inner class TextViewHolder (var view: View) : RecyclerView.ViewHolder(view) {
        val text = view.text

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@NoteItemAdapter.itemsShown.get(position) as TextEntity
            text.text = item.text
            view.setOnClickListener {
                fragment.onEditNoteOpen(items.indexOf(itemsShown[position]))
            }
        }
    }
}
