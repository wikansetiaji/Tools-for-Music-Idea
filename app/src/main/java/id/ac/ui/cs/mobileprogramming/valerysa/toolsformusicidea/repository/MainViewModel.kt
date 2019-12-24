package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity

class MainViewModel(application: Application) : ViewModel() {
    var mRepository:ItemRepository = ItemRepository(application)
    val items: LiveData<List<ItemEntity>> = mRepository.getAllItem()

    fun getDataItems():LiveData<List<ItemEntity>>{
        return items
    }

    fun addTextItem(textEntity: TextEntity){
        mRepository.insertText(textEntity)
    }

    fun editTextItem(textEntity: TextEntity){
        mRepository.updateText(textEntity)
    }

    fun addAudioItem(audioEntity: AudioEntity){
        mRepository.insertAudio(audioEntity)
    }

    fun addVideoItem(videoEntity: VideoEntity){
        mRepository.insertVideo(videoEntity)
    }
}
