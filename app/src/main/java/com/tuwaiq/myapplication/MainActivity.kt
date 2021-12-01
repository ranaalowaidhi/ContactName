package com.tuwaiq.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.ButtonBarLayout

class MainActivity : AppCompatActivity() {

    lateinit var contactName :TextView
    lateinit var contactBrn :Button

    val getContactLauncher = registerForActivityResult(
       ActivityResultContracts.PickContact()

    ){ it ->
        val queryFieleds = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

        val courser = it?.let {
            this.contentResolver.query(it,queryFieleds,null,null,null)
        }

        courser?.use {
            if (it.count == 0){
                return@use
            }
            it.moveToFirst()
            val name = it.getString(0)

            contactName.text = name
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactName = findViewById(R.id.contact_name)
        contactBrn = findViewById(R.id.contact_button)

        contactBrn.setOnClickListener {
            getContactLauncher.launch(null)
        }
    }
}