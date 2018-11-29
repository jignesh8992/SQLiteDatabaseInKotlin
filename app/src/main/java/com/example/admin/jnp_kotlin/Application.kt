package com.example.admin.jnp_kotlin

import android.content.Context
import android.graphics.Typeface
import android.os.StrictMode
import android.widget.ImageView
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType


class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        setDefaultFont(this, "DEFAULT", "app_font/Montserrat-Regular.otf")
        setDefaultFont(this, "MONOSPACE", "app_font/Montserrat-Regular.otf")
        setDefaultFont(this, "SERIF", "app_font/Montserrat-Regular.otf")
        initImageLoader(applicationContext)
    }

    fun initImageLoader(context: Context) {
        val config = ImageLoaderConfiguration.Builder(context)
        config.threadPriority(Thread.NORM_PRIORITY - 2)
        config.denyCacheImageMultipleSizesInMemory()
        config.diskCacheFileNameGenerator(Md5FileNameGenerator())
        config.diskCacheSize(50 * 1024 * 1024) // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO)
        config.writeDebugLogs() // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build())
    }

    companion object {
        fun loadFromSdcard(imageUri: String, imageView: ImageView) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage("file:///$imageUri", imageView)
        }

        fun loadFromAssets(imageUri: String, imageView: ImageView) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage("assets://$imageUri", imageView)
        }

        fun loadFromDrawable(resId: Int, imageView: ImageView) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage("drawable://$resId", imageView)
        }

        fun loadFromWeb(imageUri: String, imageView: ImageView) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(imageUri, imageView)
        }

        fun loadFromCP(imageUri: String, imageView: ImageView) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage("content://$imageUri", imageView)
        }
    }


    fun setDefaultFont(
        context: Context, TypefaceName: String, fontAssetName: String
    ) {
        val regular = Typeface.createFromAsset(
            context.getAssets(),
            fontAssetName
        )
        replaceFont(TypefaceName, regular)
    }

    fun replaceFont(
        TypefaceName: String, newTypeface: Typeface
    ) {
        try {
            val staticField = Typeface::class.java
                .getDeclaredField(TypefaceName)
            staticField.isAccessible = true
            staticField.set(null, newTypeface)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}

