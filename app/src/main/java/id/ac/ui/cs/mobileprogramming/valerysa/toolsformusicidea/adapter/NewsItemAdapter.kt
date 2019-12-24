package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.adapter

import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.NewsEntity
import kotlinx.android.synthetic.main.news_item.view.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.ListFragment
import kotlinx.android.synthetic.main.recent_text_item.view.*
import androidx.core.content.ContextCompat.startActivity
import android.content.ActivityNotFoundException





class NewsItemAdapter(val context: Context, var items:ArrayList<NewsEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TextViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TextViewHolder).bindView(position)
    }

    inner class TextViewHolder (var view: View) : RecyclerView.ViewHolder(view) {
        val text = view.title
        val open = view.open

        fun bindView(position: Int){
            this.setIsRecyclable(false)
            val item = this@NewsItemAdapter.items.get(position)
            text.text = item.title
            open.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setPackage("com.android.chrome")

                try {
                    context.startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    intent.setPackage(null)
                    context.startActivity(intent)
                }

            }
        }
    }
}
