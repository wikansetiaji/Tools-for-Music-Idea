package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.splashscreen

import android.content.Context
import android.opengl.GLSurfaceView

class SplashScreenGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: SplashScreenGLRenderer

    init {

        setEGLContextClientVersion(2)

        renderer = SplashScreenGLRenderer()

        setRenderer(renderer)
    }
}