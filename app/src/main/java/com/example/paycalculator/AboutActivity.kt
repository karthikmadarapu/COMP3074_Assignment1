package com.example.paycalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paycalculator.R


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Show the back (up) arrow in the ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_title)
    }

    // Back arrow: finish() closes this screen and returns to the EXISTING
    // MainActivity on the back stack. No new MainActivity instance is created.
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
