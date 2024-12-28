package fr.filau.polyhome.housemanagement

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.filau.polyhome.R

class HouseManagementActivity : AppCompatActivity() {
    private lateinit var apiWrapper: HouseManagementAPIWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_house_management)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiWrapper = HouseManagementAPIWrapper(this@HouseManagementActivity)
        apiWrapper.doListPeripherals()
    }

    fun setHouseIdInTitle(houseId: String) {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        headerTitle.text = "($houseId) " + headerTitle.text.toString()
    }
}
