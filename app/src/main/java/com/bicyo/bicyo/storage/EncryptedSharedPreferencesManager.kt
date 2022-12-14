package com.bicyo.bicyo.storage

import android.app.Activity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPreferencesManager (val actividad: Activity): FileHandler {
    override fun SaveInformation(datosAGrabar:Pair<String,String>){
        var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPref = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            actividad,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        val editor = sharedPref.edit()
        editor.putString(LOGIN_KEY , datosAGrabar.first)
        editor.putString(PASSWORD_KEY, datosAGrabar.second)
        editor.apply()
    }
    override fun ReadInformation():Pair<String,String>{
        var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPref = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            actividad,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        val email = sharedPref.getString(LOGIN_KEY,"").toString()
        val clave = sharedPref.getString(PASSWORD_KEY,"").toString()
        return (email to clave)
    }
}
