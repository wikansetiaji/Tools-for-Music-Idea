package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_note.save
import kotlinx.android.synthetic.main.fragment_add_note.text_input
import kotlinx.android.synthetic.main.fragment_add_note.toolbar

class EditNoteFragment(var index: Int) : Fragment() {

    companion object {
        fun newInstance(index: Int) = EditNoteFragment(index)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        (activity!! as MainActivity).hideFab()
        (activity!! as MainActivity).hideToolbar()
        text_input.setText((viewModel.getDataItems().value!![index] as TextEntity).text)
        toolbar.setNavigationOnClickListener {
            this.activity!!.onBackPressed()
        }
        save.setOnClickListener {
            if (text_input.text.isNotEmpty()){
                val textEntity = (viewModel.getDataItems().value!![index] as TextEntity)
                viewModel.editTextItem(TextEntity(text_input.text.toString(),textEntity.timestamp,textEntity.id))
                activity!!.onBackPressed()
            }
        }

    }

}
