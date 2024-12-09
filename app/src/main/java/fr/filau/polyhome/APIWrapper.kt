package fr.filau.polyhome

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtp2.Api

open class APIWrapper(protected val ui: AppCompatActivity) {
    protected var username = ""
    protected var passwordTemporary = ""
    protected var userToken = ""
    protected val api = Api()
    protected val uiNotifier = UINotifier(ui)

    init {
        loadData()
    }


    protected fun saveData() {
        for (data in arrayOf("username", "userToken")) {
            val fileOutputStream = ui.openFileOutput("$data.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(
                when (data) {
                    "username" -> username
                    "userToken" -> userToken
                    else -> ""
                }.toByteArray()
            )
            fileOutputStream.close()
        }
    }

    private fun loadData() {
        for (data in arrayOf("username", "userToken")) {
            val fileName = "$data.txt"
            if (fileName in ui.fileList()) { // Check if the file exists
                val fileInputStream = ui.openFileInput(fileName)
                val content = fileInputStream.bufferedReader().use { it.readText() }
                when (data) {
                    "username" -> username = content
                    "userToken" -> userToken = content
                }
                fileInputStream.close()
            }
        }

    }
}