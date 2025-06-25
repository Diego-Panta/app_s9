package com.example.app_s9

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class UserProfileActivity : AppCompatActivity() {
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSaveProfile: Button
    private lateinit var buttonLoadProfile: Button
    private lateinit var textViewProfileInfo: TextView

    companion object {
        const val KEY_NAME = "user_name"
        const val KEY_AGE = "user_age"
        const val KEY_EMAIL = "user_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        sharedPreferencesHelper = SharedPreferencesHelper(this)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonLoadProfile = findViewById(R.id.buttonLoadProfile)
        textViewProfileInfo = findViewById(R.id.textViewProfileInfo)
    }

    private fun setupListeners() {
        buttonSaveProfile.setOnClickListener { saveProfile() }
        buttonLoadProfile.setOnClickListener { loadProfile() }
    }

    private fun saveProfile() {
        val name = editTextName.text.toString().trim()
        val age = editTextAge.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show()
            return
        }

        sharedPreferencesHelper.saveString(KEY_NAME, name)
        sharedPreferencesHelper.saveString(KEY_AGE, age)
        sharedPreferencesHelper.saveString(KEY_EMAIL, email)

        Toast.makeText(this, R.string.profile_saved, Toast.LENGTH_SHORT).show()
    }

    private fun loadProfile() {
        val name = sharedPreferencesHelper.getString(KEY_NAME, getString(R.string.not_defined))
        val age = sharedPreferencesHelper.getString(KEY_AGE, getString(R.string.not_defined))
        val email = sharedPreferencesHelper.getString(KEY_EMAIL, getString(R.string.not_defined))

        textViewProfileInfo.text = getString(R.string.profile_info, name, age, email)

        // Mostrar el CardView con el perfil
        findViewById<CardView>(R.id.cardViewProfileInfo).visibility = View.VISIBLE
    }
}