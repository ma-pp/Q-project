package id.qproject;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.util.Log
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import android.database.Cursor
import android.net.Uri
import android.util.AttributeSet
import android.webkit.*
import android.webkit.JavascriptInterface;

class ActsDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    private fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }
    
    @Synchronized
    private fun installOrUpdateIfNecessary() {
            Log.v( "qproject",  "installOrUpdateIfNecessary")
        if (installedDatabaseIsOutdated()) {
            Log.v( "qproject", "installOrUpdateIfNecessary")
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        throw RuntimeException("The $DATABASE_NAME database is not writable.")
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Nothing to do
            Log.v( "qproject", "onCreate(db: SQLiteDatabase?)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nothing to do
    }

    companion object {
        const val ASSETS_PATH = ""
        const val DATABASE_NAME = "quran.db"
        const val DATABASE_VERSION = 1
    }

}

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This is an example, remember to use a background thread in production.
        val myDatabase = ActsDbHelper(this).getReadableDatabase() //readableDatabase
        myDatabase.rawQuery("SELECT * FROM words WHERE page=1", null)
        
        val webView = findViewById(R.id.webview) as WebView
        if (webView != null) {
 
            webView!!.loadData("<html><body><p>hello</p></body></html>", "text/html", "UTF-8")
        }
    }
}




/*
09-14 01:12:31.977  8360  8360 E AndroidRuntime: Caused by: java.lang.ClassNotFoundException: Didn't find class "kotlin.jvm.internal.Intrinsics" on path: DexPathList[[zip file "/data/app/umar.hello-ucPrILX_ICA6AslQc1-Fpw==/base.apk"],nativeLibraryDirectories=[/data/app/umar.hello-ucPrILX_ICA6AslQc1-Fpw==/lib/arm, /system/lib, /system/vendor/lib]]

 */