package com.example.admin.jnp_kotlin.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.admin.jnp_kotlin.R
import java.io.File


class AppHelper {

    companion object {

        private val log_tag = "JNP__" + AppHelper::class.java!!.simpleName
        private val log = true
        private val toast = true


        /**
         * ToDo. Print Log message
         *
         * @param message The message to print
         */
        fun log(message: String) {
            if (log) {
                Log.i(log_tag, message)
            }
        }

        /**
         * ToDo. Print Log message
         *
         * @param message The message to print
         */
        fun log(message: Int) {
            if (log) {
                Log.i(log_tag, (message).toString() + "")
            }
        }

        /**
         * ToDo. Print Log message
         *
         * @param exception The exception to print
         */
        fun log(exception: Exception) {
            if (log) {
                Log.i(log_tag, exception.toString())
            }
        }

        /**
         * ToDo. Print Log message
         *
         * @param tag     The tag
         * @param message The message to print
         */
        fun log(tag: String, message: String) {
            if (log) {
                Log.i(tag, message)
            }
        }

        /**
         * ToDo. Print Log message
         *
         * @param tag     The tag
         * @param message The message to print
         */
        fun log(tag: String, message: Int) {
            if (log) {
                Log.i(tag, (message).toString() + "")
            }
        }

        /**
         * ToDo. Print Log message
         *
         * @param tag       The tag
         * @param exception The exception to print
         */
        fun log(tag: String, exception: Exception) {
            if (log) {
                Log.i(tag, exception.toString())
            }
        }

        /**
         * ToDo. Toast message
         *
         * @param mContext The mContext
         * @param message  The message to toast
         */
        fun toast(mContext: Context, message: String) {
            if (toast) {
                val toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        /**
         * ToDo. Toast message
         *
         * @param mContext The mContext
         * @param message  The message to toast
         */
        fun toast(mContext: Context, message: Int) {
            if (toast) {
                val toast = Toast.makeText(mContext, (message).toString() + "", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        /**
         * ToDo. Toast message
         *
         * @param mContext The mContext
         * @param bitmap   The message to toast
         */
        fun toast(mContext: Context, bitmap: Bitmap) {
            val toast = Toast(mContext)
            val imageView = ImageView(mContext)
            imageView.setImageBitmap(bitmap)
            toast.view = imageView
            toast.show()
        }


        /**
         * ToDo.. Get Application username from string resource
         *
         * @param mContext The mContext
         */
        fun getAppName(mContext: Context): String {
            return mContext.getString(R.string.app_name)
        }


        /**
         * ToDo.. Get path of app username folder
         *
         * @param mContext The mContext
         */
        fun getOutputPath(mContext: Context): String {
            val path = Environment.getExternalStorageDirectory()
            path.appendText(File.separator)
            path.appendText(getAppName(mContext))
            if (!path.exists()) {
                path.mkdir()
            }
            return path.path
        }

        /**
         * ToDo.. Share our application url
         *
         * @param mContext The mContext
         */
        fun shareApp(mContext: Context) {

            // change message as per app
            val text = "I am using the %1\$s app: %1\$s. if you want to have a try, " + "please " +
                    "search: \"%2\$s\" in play store!,  Or Click on the link given below to " +
                    "download. "

            val link = "https://play.google.com/store/apps/details?id=" + mContext.packageName

            val msg = String.format(text,
                getAppName(mContext),
                getAppName(mContext),
                getAppName(mContext)
            )

            val i = Intent("android.intent.action.SEND")
            i.putExtra("android.intent.extra.TEXT", msg + link)
            i.type = "text/plain"
            mContext.startActivity(Intent.createChooser(i, "Share Via"))
        }

        /**
         * ToDo.. Open application in google playstore for giving rating our app
         *
         * @param mContext The mContext
         */
        fun rateApp(mContext: Context) {
            try {
                val marketUri = Uri.parse("market://details?id=" + mContext.packageName)
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                mContext.startActivity(marketIntent)
            } catch (e: ActivityNotFoundException) {
                val marketUri = Uri.parse(
                    "https://play.google.com/store/apps/details?id=" + mContext
                        .packageName
                )
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                mContext.startActivity(marketIntent)
            }

        }

        /**
         * ToDo. Open our more apps on playstore
         *
         * @param mContext The mContext
         */
        fun moreApp(mContext: Context) {
            try {
                val query = "pub:" + "Rakta Tech" // change query here
                val intent = Intent("android.intent.action.VIEW", Uri.parse("market://search?q=$query"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                mContext.startActivity(intent)

            } catch (e: Exception) {
            }

        }


        /**
         * ToDo.. Return true if internet or wi-fi connection is working fine
         *
         *
         * Required permission
         * <uses-permission android:username="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
         * <uses-permission android:username="android.permission.INTERNET"></uses-permission>
         *
         * @param mContext The mContext
         * @return true if you have the internet connection, or false if not.
         */
        fun isOnline(mContext: Context): Boolean {
            var haveConnectedWifi = false
            var haveConnectedMobile = false

            val cm = mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork!!.type == ConnectivityManager.TYPE_WIFI) {
                    if (activeNetwork!!.isConnected)
                        haveConnectedWifi = true
                } else if (activeNetwork!!.type == ConnectivityManager.TYPE_MOBILE) {
                    if (activeNetwork!!.isConnected)
                        haveConnectedMobile = true
                }
            }
            return haveConnectedWifi || haveConnectedMobile
        }
    }


}