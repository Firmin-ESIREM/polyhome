package fr.filau.polyhome


import android.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UINotifier (private val ui: AppCompatActivity) {

    private fun notify(message: String, parameter: String = "") {
        var messageTitle = ""
        var messageContent = ""
        when (message) {
            "unknown" -> {
                messageTitle = "Oups…"
                messageContent = "Une erreur inconnue a eu lieu pendant $parameter."
            }
            "serverSide" -> {
                messageTitle = "Oups…"
                messageContent = "Une erreur serveur a eu lieu pendant $parameter."
            }
            "badRequest" -> {
                messageTitle = "Oups…"
                messageContent = "Une erreur « bad request » a eu lieu pendant $parameter. Cette erreur vient sans doute d’un problème dans l’application."
            }
            "forbiddenError" -> {
                messageTitle = "C’est verrouillé…"
                messageContent = "Vous n’avez pas accès à cette ressource. Cette erreur vient sans doute d’un problème dans l’application."
            }
            "invalidCredentials" -> {
                messageTitle = "Mauvais identifiants"
                messageContent = "Les identifiants que vous avez spécifiés sont invalides."
            }
            "usernameAlreadyInUse" -> {
                messageTitle = "Collision !"
                messageContent = "Le nom d’utilisateur que vous avez spécifié est déjà utilisé."
            }
        }
        ui.runOnUiThread {
            AlertDialog
                .Builder(ui)
                .setTitle(messageTitle)
                .setMessage(messageContent)
                .show()
        }
     }


    fun unknownError(duringWhat: String) {
        notify("unknown", duringWhat)
    }

    fun serverError(duringWhat: String) {
        notify("serverSide", duringWhat)
    }

    fun badRequestError(duringWhat: String) {
        notify("badRequest", duringWhat)
    }

    fun forbiddenError() {
        notify("forbiddenError")
    }

    fun invalidCredentials() {
        notify("invalidCredentials")
    }

    fun usernameAlreadyInUse() {
        notify("usernameAlreadyInUse")
    }

    fun bravo() {
        ui.runOnUiThread {
            AlertDialog
                .Builder(ui)
                .setTitle("bg")
                .setMessage("t tro for")
                .show()
        }
    }

    fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        ui.runOnUiThread {
            Toast.makeText(ui, content, duration).show()
        }
    }
}