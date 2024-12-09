package fr.filau.polyhome

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AccountActivity : AppCompatActivity() {
    private var accountsApiWrapper = AccountsAPIWrapper(this@AccountActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.houseicon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    public fun login(view: View) {
        val username = findViewById<EditText>(R.id.usernameField).text.toString()
        val password = findViewById<EditText>(R.id.passwordField).text.toString()
        accountsApiWrapper.doLogin(username, password)
    }

    public fun register(view: View) {
        val username = findViewById<EditText>(R.id.usernameField).text.toString()
        val password = findViewById<EditText>(R.id.passwordField).text.toString()
        accountsApiWrapper.doRegister(username, password)
    }
}