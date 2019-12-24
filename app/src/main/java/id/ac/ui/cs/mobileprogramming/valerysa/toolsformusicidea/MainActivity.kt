package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.CustomViewModelFactory
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.repository.MainViewModel
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_content.*
import java.text.SimpleDateFormat
import java.util.*

const val CHANNEL_ID = "notifchannel"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri = data!!.data!!
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            viewModel.addVideoItem(VideoEntity(videoUri.toString(),"Video ${timeStamp}",Calendar.getInstance().time))
            val toast = Toast.makeText(this, "Video saved", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    val REQUEST_VIDEO_CAPTURE = 1

    private fun dispatchTakeVideoIntent() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }
        else{
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                takeVideoIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.note -> {
                onNotePageOpen()
                toolbar.title=getString(R.string.note)
            }
            R.id.recent -> {
                onRecentPageOpen()
                toolbar.title=getString(R.string.recent)
            }
            R.id.audio -> {
                onAudioPageOpen()
                toolbar.title=getString(R.string.audio)
            }
            R.id.video -> {
                onVideoPageOpen()
                toolbar.title=getString(R.string.video)
            }
            R.id.news -> {
                onSongNewsOpen()
                toolbar.title="Top Music News"
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun hideFab(){
        fabClose()
        fab.visibility = View.GONE
    }

    fun showFab(){
        fab.visibility = View.VISIBLE
    }

    fun hideToolbar(){
        toolbar.visibility = View.GONE
    }

    fun showToolbar(){
        toolbar.visibility = View.VISIBLE
    }

    fun fabOpen(){
        fab_open.hide()
        fab_close.show()
        fab_audio.show()
        fab_text.show()
        fab_image.show()
    }

    fun fabClose(){
        fab_open.show()
        fab_close.hide()
        fab_audio.hide()
        fab_text.hide()
        fab_image.hide()
    }

    fun onAddNoteOpen() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, AddNoteFragment.newInstance())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun onRecordAudioOpen() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }
        else{
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, RecordAudioFragment.newInstance())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    fun onNotePageOpen() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, NoteFragment.newInstance())
        fragmentTransaction.commit()
    }

    fun onRecentPageOpen() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, MainFragment.newInstance())
        fragmentTransaction.commit()
    }

    fun onAudioPageOpen() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, AudioFragment.newInstance())
        fragmentTransaction.commit()
    }

    fun onVideoPageOpen() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, VideoFragment.newInstance())
        fragmentTransaction.commit()
    }

    fun onPlayAudioOpen(index:Int){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val dialogFragment = PlayAudioFragment(index)
        dialogFragment.show(fragmentTransaction, "dialog")
    }

    fun onSongNewsOpen(){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, SongNewsFragment.newInstance())
        fragmentTransaction.commit()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel1"
            val descriptionText = "record notif"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var builder: NotificationCompat.Builder

    private lateinit var stopRecordingIntent:Intent
    private lateinit var stopRecordingPendingIntent:PendingIntent

    private lateinit var pauseRecordingIntent:Intent
    private lateinit var pauseRecordingPendingIntent:PendingIntent

    private lateinit var resumeRecordingIntent:Intent
    private lateinit var resumeRecordingPendingIntent:PendingIntent


    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        val fragManager = this.supportFragmentManager
        val count = this.supportFragmentManager.backStackEntryCount
        val fragment= fragManager.fragments[if (count > 0) count - 1 else count] as RecordAudioFragment
        if (newIntent.getStringExtra("type")=="stop"){
            closeRecordNotification()
            fragment.stopRecording()
        }
        else if (newIntent.getStringExtra("type")=="pause"){
            pauseRecordNotification()
            fragment.pauseRecording()
        }
        else if (newIntent.getStringExtra("type")=="resume"){
            showRecordNotification()
            fragment.resumeRecording()
        }
        Log.v("notif",newIntent.getStringExtra("type"))
    }

    fun showRecordNotification(){
        with(NotificationManagerCompat.from(this)) {
            builder.setSmallIcon(R.drawable.ic_fiber_manual_record_black_24dp)
            builder.setContentTitle("Now recording")
            builder.mActions.removeAt(1)
            builder.addAction(R.drawable.ic_pause_black_24dp, "Pause",
                pauseRecordingPendingIntent)
            notify(0, builder.build())
        }
    }

    fun closeRecordNotification(){
        with(NotificationManagerCompat.from(this)) {
            cancel(0)
        }
    }

    fun pauseRecordNotification(){
        with(NotificationManagerCompat.from(this)) {
            builder.setSmallIcon(R.drawable.ic_pause_black_24dp)
            builder.setContentTitle("Paused")
            builder.mActions.removeAt(1)
            builder.addAction(R.drawable.ic_play_arrow_black_24dp, "Resume",
                resumeRecordingPendingIntent)
            notify(0,builder.build())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(ConnectionChangedBroadcastReceiver(), intentFilter)

        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(application)).get(MainViewModel::class.java)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        fab_open.setOnClickListener {
            fabOpen()
        }
        fab_close.setOnClickListener {
            fabClose()
        }
        fab_text.setOnClickListener {
            onAddNoteOpen()
        }
        fab_audio.setOnClickListener {
            onRecordAudioOpen()
        }
        fab_image.setOnClickListener {
            dispatchTakeVideoIntent()
        }
        toolbar.setNavigationOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.menu.get(0).isChecked = true
        toolbar.title="Recent"

        createNotificationChannel()

        stopRecordingIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("type","stop")
            addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        stopRecordingPendingIntent = PendingIntent.getActivity(this,0,stopRecordingIntent,0)

        pauseRecordingIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("type","pause")
            addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        pauseRecordingPendingIntent = PendingIntent.getActivity(this,1,pauseRecordingIntent,0)

        resumeRecordingIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("type","resume")
            addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        resumeRecordingPendingIntent = PendingIntent.getActivity(this,2,resumeRecordingIntent,0)

        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_fiber_manual_record_black_24dp)
            .setContentTitle("Now recording")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .addAction(R.drawable.ic_pause_black_24dp, "Stop",
                stopRecordingPendingIntent)
            .addAction(R.drawable.ic_pause_black_24dp, "Pause",
                pauseRecordingPendingIntent)
    }

    fun verifyAvailableNetwork():Boolean{
        val connectivityManager=this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    fun toggleLyricButton(active:Boolean){
        nav_view.menu[4].isEnabled=active
    }

    inner class ConnectionChangedBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.v("CONNECTIVITY","Changed to: "+verifyAvailableNetwork().toString())
            if (verifyAvailableNetwork()){
                toggleLyricButton(true)
            }
            else{
                toggleLyricButton(false)
            }
        }
    }

    external fun apiKeyFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

}
