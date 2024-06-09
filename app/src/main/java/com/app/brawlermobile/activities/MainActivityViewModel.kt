package com.app.brawlermobile.activities

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.app.brawlermobile.json.JsonParser
import com.caverock.androidsvg.SVG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _isReady = MutableStateFlow(false)
    private val _svgImages = mutableStateMapOf<String, BitmapDrawable?>()
    private val _colors = mutableStateOf<Map<String, Map<String, String>>?>(null)
    private val _svgSize = 20

    val isReady = _isReady.asStateFlow()

    val expanded = mutableStateOf(false)
    val expandedItems = mutableStateMapOf<String, Boolean>()
    val svgImages: Map<String, BitmapDrawable?> get() = _svgImages
    val colors: Map<String, Map<String, String>>? get() = _colors.value
    val svgSize: Int get() = _svgSize

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val images = JsonParser.getImages(getApplication())
            val colorsData = JsonParser.getColors(getApplication())
            withContext(Dispatchers.Main) {
                _colors.value = colorsData
            }
            images?.forEach { (key, imageUrl) ->
                val url = URL(imageUrl)
                val svg = SVG.getFromInputStream(url.openConnection().getInputStream())
                val bitmap = Bitmap.createBitmap(_svgSize, _svgSize, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                svg.renderToCanvas(canvas)
                val drawable = BitmapDrawable(application.resources, bitmap)

                withContext(Dispatchers.Main) {
                    _svgImages[key] = drawable
                }
            }
            _isReady.value = true
        }
    }

    // Appbar

    private val _appBarText = MutableStateFlow("Home")
    val appBarText: StateFlow<String> = _appBarText

    fun setAppBarText(newText: String) {
        _appBarText.value = newText
    }
}