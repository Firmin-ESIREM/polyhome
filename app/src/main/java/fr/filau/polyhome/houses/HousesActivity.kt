package fr.filau.polyhome.houses

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.filau.polyhome.R

class HousesActivity : AppCompatActivity() {
    private val apiWrapper = HousesAPIWrapper(this@HousesActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_houses)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiWrapper.doListHouses()
        findViewById<ImageView>(R.id.userPicture).setOnClickListener {
            apiWrapper.logoutManager()
        }

    }
}
