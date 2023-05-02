package com.example.contacts_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast


class ContactsAdd : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_add)

        firstNameEditText = findViewById(R.id.firstname_input)
        surnameEditText = findViewById(R.id.surname_input)
        phoneEditText = findViewById(R.id.phone_input)
        emailEditText = findViewById(R.id.email_input)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        val saveButton = findViewById<Button>(R.id.save_button)


        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveButton.setOnClickListener {
            val firstname = firstNameEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            if(firstname.isEmpty()){
                firstNameEditText.error = "Firstname is Required"
                return@setOnClickListener
            }else if (phone.isEmpty()){
                phoneEditText.error = "Phone number is Required"
                return@setOnClickListener
            }else if(isEmailValid(email)){
                emailEditText.error = "A valid Email is Required"
                return@setOnClickListener
            }else{
                onSaveButtonClick(firstname, surname, phone, email)
            }
        }
    }

    private fun onSaveButtonClick(firstname: String, surname: String, phone: String, email: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("firstName", firstname)
        intent.putExtra("surname", surname)
        intent.putExtra("phone", phone)
        intent.putExtra("email", email)

        setResult(Activity.RESULT_OK, intent)

        Toast.makeText(this,"Contact added", Toast.LENGTH_SHORT).show()
        // Finish the activity and return to the MainActivity
        finish()
    }
    private fun isEmailValid(email: String): Boolean {
        if(email.isBlank()){
            return true
        }

        val pattern = Regex("^\\S+@\\S+\\.\\S+\$")
        return pattern.matches(email)
    }

}


