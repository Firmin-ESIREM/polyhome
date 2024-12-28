package fr.filau.polyhome.generic

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.filau.polyhome.generic.external.Api
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

private val Context.tokenStore by preferencesDataStore(name = "token")

open class APIWrapper(protected val ui: AppCompatActivity) {
    protected var username = ""
    protected var passwordTemporary = ""
    protected var userToken = ""
    protected val api = Api()
    protected val uiNotifier = UINotifier(ui)
    private var userKey = stringPreferencesKey("username")
    private var tokenKey = stringPreferencesKey("token")

    init {
        runBlocking { loadData() }
    }


    protected suspend fun saveData() {
        ui.tokenStore.edit { preferences ->
            preferences[userKey] = username;
            preferences[tokenKey] = userToken;
        }
    }

    private suspend fun loadData() {
        val tokenStoreUserKey = ui.tokenStore.data.firstOrNull()?.get(userKey)
        val tokenStoreUserToken = ui.tokenStore.data.firstOrNull()?.get(tokenKey)

        if (tokenStoreUserKey != null) {
            username = tokenStoreUserKey
        }
        if (tokenStoreUserToken != null) {
            userToken = tokenStoreUserToken
        }

    }
}