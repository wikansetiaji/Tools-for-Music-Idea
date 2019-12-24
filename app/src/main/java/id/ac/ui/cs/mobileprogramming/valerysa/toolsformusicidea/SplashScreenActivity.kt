package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea

import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.splashscreen.SplashScreenGLSurfaceView

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var gLView: GLSurfaceView
    var background: Thread = object : Thread() {
        override fun run() {
            try {
                sleep((3 * 1000).toLong())
                val intent = Intent(applicationContext,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } catch (e: Exception) {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gLView = SplashScreenGLSurfaceView(this)
        setContentView(gLView)

        background.start()
    }

}
