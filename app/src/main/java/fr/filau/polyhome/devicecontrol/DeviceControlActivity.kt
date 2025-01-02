package fr.filau.polyhome.devicecontrol

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.filau.polyhome.R

class DeviceControlActivity : AppCompatActivity() {
    private val apiWrapper = DeviceControlAPIWrapper(this@DeviceControlActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_device_control)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
        apiWrapper.setDevice()
    }
}
