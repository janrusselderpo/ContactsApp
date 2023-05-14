package com.example.contacts_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.contacts_app.databinding.ActivityContactsAddBinding


class ContactsAdd : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var binding: ActivityContactsAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstNameEditText = binding.firstnameInput
        surnameEditText = binding.surnameInput
        phoneEditText = binding.phoneInput
        emailEditText = binding.emailInput

        val backButton = binding.backButton
        val saveButton = binding.saveButton


        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveButton.setOnClickListener {
            val firstname = binding.firstnameInput.text.toString().trim()
            val surname = binding.surnameInput.text.toString().trim()
            val phone = binding.phoneInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()

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


