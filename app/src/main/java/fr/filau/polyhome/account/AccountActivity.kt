package fr.filau.polyhome.account

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.filau.polyhome.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AccountActivity : AppCompatActivity() {
    private lateinit var apiWrapper: AccountsAPIWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.houseicon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        MainScope().launch {
            apiWrapper = AccountsAPIWrapper(this@AccountActivity)
        }
    }

    fun login(view: View) {
        val username = findViewById<EditText>(R.id.usernameField).text.toString()
        val password = findViewById<EditText>(R.id.passwordField).text.toString()
        apiWrapper.doLogin(username, password)
    }

    fun register(view: View) {
        val username = findViewById<EditText>(R.id.usernameField).text.toString()
        val password = findViewById<EditText>(R.id.passwordField).text.toString()
        apiWrapper.doRegister(username, password)
    }
}
