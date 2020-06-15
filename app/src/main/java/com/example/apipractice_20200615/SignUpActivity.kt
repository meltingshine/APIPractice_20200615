package com.example.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignUpActivity : BaseActivity() {
    override fun setupEvents() {

    }

    override fun setValues() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }
}
