package com.example.app_s9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextUsername: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button
    private lateinit var buttonClear: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonResetCounter: Button
    private lateinit var textViewVisitCount: TextView
    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var buttonGoToProfile: Button

    companion object {
        const val KEY_VISIT_COUNT = "visit_count"
        const val KEY_APP_IN_FOREGROUND = "app_in_foreground"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración edge-to-edge
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
        checkFirstTime()
        handleAppLaunch()
        updateVisitCountDisplay()
    }

    private fun initViews() {
        editTextUsername = findViewById(R.id.editTextUsername)
        buttonSave = findViewById(R.id.buttonSave)
        buttonLoad = findViewById(R.id.buttonLoad)
        buttonClear = findViewById(R.id.buttonClear)
        textViewResult = findViewById(R.id.textViewResult)
        buttonResetCounter = findViewById(R.id.buttonResetCounter)
        textViewVisitCount = findViewById(R.id.textViewVisitCount)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        buttonGoToProfile = findViewById(R.id.buttonGoToProfile)
    }

    private fun handleAppLaunch() {
        val wasInForeground = sharedPreferencesHelper.getBoolean(KEY_APP_IN_FOREGROUND, false)

        if (!wasInForeground && !isChangingConfigurations) {
            incrementVisitCount()
        }

        sharedPreferencesHelper.saveBoolean(KEY_APP_IN_FOREGROUND, true)
    }

    private fun setupListeners() {
        buttonSave.setOnClickListener { saveData() }
        buttonLoad.setOnClickListener { loadData() }
        buttonClear.setOnClickListener { clearAllData() }
        buttonResetCounter.setOnClickListener { resetVisitCount() }
        buttonGoToProfile.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
        switchDarkMode.setOnCheckedChangeListener(null)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (switchDarkMode.isPressed) {
                sharedPreferencesHelper.saveBoolean(
                    SharedPreferencesHelper.KEY_THEME_MODE,
                    isChecked
                )
                window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                recreate()
            }
        }
        updateSwitchState()
    }

    private fun applyTheme() {
        val isDarkMode = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_THEME_MODE, false)
        if (isDarkMode) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }
    }

    private fun updateSwitchState() {
        val isDarkMode =
            sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_THEME_MODE, false)
        switchDarkMode.isChecked = isDarkMode
    }

    private fun incrementVisitCount() {
        val currentCount = sharedPreferencesHelper.getInt(KEY_VISIT_COUNT, 0)
        sharedPreferencesHelper.saveInt(KEY_VISIT_COUNT, currentCount + 1)
        updateVisitCountDisplay()
    }


    private fun resetVisitCount() {
        sharedPreferencesHelper.saveInt(KEY_VISIT_COUNT, 0)
        updateVisitCountDisplay()
        Toast.makeText(this, R.string.counter_reset, Toast.LENGTH_SHORT).show()
    }

    private fun updateVisitCountDisplay() {
        textViewVisitCount.text = getString(
            R.string.visit_count,
            sharedPreferencesHelper.getInt(KEY_VISIT_COUNT, 0)
        )
    }

    private fun saveData() {
        val username = editTextUsername.text.toString().trim()

        if (username.isEmpty()) {
            Toast.makeText(this, R.string.enter_name, Toast.LENGTH_SHORT).show()
            return
        }

        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_USERNAME, username)
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_USER_ID, (1000..9999).random())

        Toast.makeText(this, R.string.data_saved, Toast.LENGTH_SHORT).show()
        editTextUsername.setText("")
    }

    private fun loadData() {
        val username = sharedPreferencesHelper.getString(
            SharedPreferencesHelper.KEY_USERNAME, getString(R.string.not_defined)
        )
        val isFirstTime = sharedPreferencesHelper.getBoolean(
            SharedPreferencesHelper.KEY_IS_FIRST_TIME, true
        )
        val userId = sharedPreferencesHelper.getInt(
            SharedPreferencesHelper.KEY_USER_ID, 0
        )

        textViewResult.text = getString(
            R.string.user_info,
            username,
            userId,
            if (isFirstTime) getString(R.string.yes) else getString(R.string.no)
        )
    }

    private fun clearAllData() {
        sharedPreferencesHelper.clearAll()
        textViewResult.text = ""
        editTextUsername.setText("")
        updateVisitCountDisplay()
        Toast.makeText(this, R.string.all_data_cleared, Toast.LENGTH_SHORT).show()
    }

    private fun checkFirstTime() {
        if (sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)) {
            if (!isChangingConfigurations) {
                Toast.makeText(this, R.string.welcome_first_time, Toast.LENGTH_LONG).show()
            }
            sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            // Verificar si la app realmente va a segundo plano
            if (isFinishing || !isTaskRoot) {
                sharedPreferencesHelper.saveBoolean(KEY_APP_IN_FOREGROUND, false)
            }
        }
    }


}