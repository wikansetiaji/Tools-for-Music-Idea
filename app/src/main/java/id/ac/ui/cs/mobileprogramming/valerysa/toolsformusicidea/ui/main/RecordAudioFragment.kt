package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.MainActivity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.R
import kotlinx.android.synthetic.main.fragment_add_note.toolbar
import kotlinx.android.synthetic.main.fragment_record_audio.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.os.*
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel


class RecordAudioFragment : Fragment() {

    companion object {
        fun newInstance() = RecordAudioFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false

    var timeStamp:String=""

    private var startTime:Long = 0L
    private var timerHandler:Handler = Handler()
    var timeInMilliseconds:Long = 0L
    var timeSwapBuff:Long = 0L
    var timeChange:Long = 0L

    private var updateTimeUp:Runnable = object: Runnable{
        override fun run(){
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime

            timeChange = timeSwapBuff + timeInMilliseconds

            val second:Long = (timeChange/1000)%60
            val minute:Long = (timeChange/60000)%60
            val hour:Long = (timeChange/3600000)

            time.text = "${hour}:${minute}:${second}"

            timerHandler.postDelayed(this, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_record_audio, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        (activity!! as MainActivity).hideFab()
        (activity!! as MainActivity).hideToolbar()
        toolbar.setNavigationOnClickListener {
            this.activity!!.onBackPressed()
        }
        timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        output = Environment.getExternalStorageDirectory().absolutePath + "/Audio ${timeStamp}.mp3"
        mediaRecorder = MediaRecorder()

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)

        record.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(activity!!, permissions,0)
            } else {
                (activity!! as MainActivity).showRecordNotification()
                startRecording()
            }
        }

        stop.setOnClickListener{
            (activity!! as MainActivity).closeRecordNotification()
            stopRecording()
        }

        pause.setOnClickListener {
            if(!recordingStopped){
                (activity!! as MainActivity).pauseRecordNotification()
            }
            else{
                (activity!! as MainActivity).showRecordNotification()
            }
            pauseRecording()
        }

    }

    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            startTime = SystemClock.uptimeMillis();
            timerHandler.postDelayed(updateTimeUp, 0)
            state = true
            record.visibility=View.INVISIBLE
            pause.visibility=View.VISIBLE
            Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            timeSwapBuff += timeInMilliseconds
            timerHandler.removeCallbacks(updateTimeUp)
            mediaRecorder?.release()
            state = false
            viewModel.addAudioItem(AudioEntity("Audio ${timeStamp}.mp3",Calendar.getInstance().time))
            Toast.makeText(activity, "Recording saved", Toast.LENGTH_SHORT).show()
            activity!!.onBackPressed()
        }else{
            Toast.makeText(activity, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun pauseRecording() {
        if(state) {
            if(!recordingStopped){
                Toast.makeText(this.activity,"Stopped!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                timeSwapBuff += timeInMilliseconds;
                timerHandler.removeCallbacks(updateTimeUp);
                recordingStopped = true
                pause.text = "Resume"
            }else{
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun resumeRecording() {
        Toast.makeText(this.activity,"Resume!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(updateTimeUp, 0)
        pause.text = "Pause"
        recordingStopped = false
    }

}
