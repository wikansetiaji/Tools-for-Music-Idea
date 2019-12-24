package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository

import android.app.Application
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.AudioDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.TextDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.VideoDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database.AudioDatabase
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database.TextDatabase
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database.VideoDatabase
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity




class ItemRepository( application: Application) {
    private var textDao: TextDao = TextDatabase.getInstance(application)!!.textDao()
    private var audioDao:AudioDao = AudioDatabase.getInstance(application)!!.audioDao()
    private var videoDao:VideoDao = VideoDatabase.getInstance(application)!!.videoDao()

    fun getAllItem(): LiveData<List<ItemEntity>> {
        val textItems = textDao.getAll() as LiveData<List<ItemEntity>>
        val audioItems = audioDao.getAll() as LiveData<List<ItemEntity>>
        val videoItems = videoDao.getAll() as LiveData<List<ItemEntity>>
        val itemLiveData = MediatorLiveData<List<ItemEntity>>()
        itemLiveData.addSource(textItems) {
                itemLiveData.value = combineLiveData(textItems, audioItems, videoItems)
        }
        itemLiveData.addSource(audioItems) {
                itemLiveData.value = combineLiveData(textItems, audioItems, videoItems)
        }
        itemLiveData.addSource(videoItems) {
                itemLiveData.value = combineLiveData(textItems, audioItems, videoItems)
        }
        return itemLiveData
    }

    fun combineLiveData(liveData1: LiveData<List<ItemEntity>>, liveData2: LiveData<List<ItemEntity>>, liveData3: LiveData<List<ItemEntity>>):List<ItemEntity>{
        val temp:ArrayList<ItemEntity> = arrayListOf()
        if (liveData1.value!=null){
            temp.addAll(liveData1.value!!)
        }
        if (liveData2.value!=null){
            temp.addAll(liveData2.value!!)
        }
        if (liveData3.value!=null){
            temp.addAll(liveData3.value!!)
        }
        return temp.sortedWith(compareBy{ it.getTimeStamp() }).reversed()
    }

    fun insertText(text: TextEntity) {
        insertTextAsyncTask(textDao).execute(text)
    }

    fun updateText(text: TextEntity){
        updateTextAsyncTask(textDao).execute(text)
    }

    fun insertAudio(audio: AudioEntity) {
        insertAudioAsyncTask(audioDao).execute(audio)
    }

    fun insertVideo(video: VideoEntity) {
        insertVideoAsyncTask(videoDao).execute(video)
    }

    private class updateTextAsyncTask internal constructor(private val mAsyncTaskDao: TextDao) :
        AsyncTask<TextEntity, Void, Void>() {

        override fun doInBackground(vararg params: TextEntity): Void? {
            mAsyncTaskDao.update(params[0].id,params[0].text,params[0].timestamp)
            return null
        }
    }

    private class insertTextAsyncTask internal constructor(private val mAsyncTaskDao: TextDao) :
        AsyncTask<TextEntity, Void, Void>() {

        override fun doInBackground(vararg params: TextEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAudioAsyncTask internal constructor(private val mAsyncTaskDao: AudioDao) :
        AsyncTask<AudioEntity, Void, Void>() {

        override fun doInBackground(vararg params: AudioEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertVideoAsyncTask internal constructor(private val mAsyncTaskDao: VideoDao) :
        AsyncTask<VideoEntity, Void, Void>() {

        override fun doInBackground(vararg params: VideoEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

}