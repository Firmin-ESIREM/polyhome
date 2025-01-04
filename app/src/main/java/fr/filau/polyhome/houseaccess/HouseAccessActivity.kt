package fr.filau.polyhome.houseaccess

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.filau.polyhome.R


class HouseAccessActivity : AppCompatActivity() {
    private lateinit var apiWrapper: HouseAccessAPIWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_house_access)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
        findViewById<ImageButton>(R.id.addUserButton).setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Ajouter un utilisateur")
            builder.setMessage("Saisissez le nom de l’utilisateur à ajouter.")

            val input = EditText(this)

            input.inputType = InputType.TYPE_CLASS_TEXT

            builder.setView(input)
            builder.setPositiveButton("Ajouter") { _, _ ->
                val username = input.text.toString()
                apiWrapper.addUser(username)
                (findViewById<ListView>(R.id.accessList).adapter as HouseAccessAdapter).addUserToList(username)
            }
            builder.setNegativeButton("Annuler") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }


        apiWrapper = HouseAccessAPIWrapper(this@HouseAccessActivity)
        apiWrapper.doListUsers()
    }

    fun setHouseIdInTitle(houseId: String) {
        val headerTitle = findViewById<TextView>(R.id.headerTitle)
        headerTitle.text = "($houseId) " + headerTitle.text.toString()
    }
}
