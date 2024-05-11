package com.example.simplenotes

import android.content.Context
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeRadioGroup: RadioGroup = findViewById(R.id.themeRadioGroup)
        val lightThemeRadioButton: RadioButton = findViewById(R.id.lightTheme)
        val darkThemeRadioButton: RadioButton = findViewById(R.id.darkTheme)

        // Загрузка сохраненной темы
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val isDarkTheme = sharedPref.getBoolean("DarkTheme", false)
        if (isDarkTheme) {
            darkThemeRadioButton.isChecked = true
        } else {
            lightThemeRadioButton.isChecked = true
        }

        themeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val edit = sharedPref.edit()
            if (checkedId == R.id.darkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                edit.putBoolean("DarkTheme", true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                edit.putBoolean("DarkTheme", false)
            }
            edit.apply()
        }
    }
}