package com.example.contacts_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var contactList: ArrayList<Contacts>
    private lateinit var adapter: ContactsAdapter
    private lateinit var adapterBinary: ContactsAdapterBinary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val addContactBtn = findViewById<FloatingActionButton>(R.id.add_contact)
        val searchView = findViewById<SearchView>(R.id.searchView)

       addData()

        adapterBinary = ContactsAdapterBinary(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactsAdapterBinary(contactList)
        recyclerView.adapter = adapterBinary

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                executeBinarySearch(newText)
                return true
            }
        })

        addContactBtn.setOnClickListener {
            val intent = Intent(this, ContactsAdd::class.java)
            startActivity(intent)
        }
    }
    private fun executeFilterSearch(newText:String){
        if (newText.isNotEmpty()) {
            adapter.filter.filter("")
        } else {
            val startTime = System.nanoTime()
            adapter.filter.filter(newText.trim())
            val endTime = System.nanoTime()
            val executionTime = endTime - startTime
            val executionTimeInMs = executionTime / 1_000_000.0
            Log.d("Search", "Execution time: ${executionTimeInMs}ms")
        }
    }
    private fun executeBinarySearch(newText: String?){
        if (!newText.isNullOrEmpty()) {
            val query = newText.lowercase(Locale.getDefault())
            val result = binarySearch(contactList, query)
            if (result != -1) {
                val filteredList = arrayListOf(contactList[result])
                adapterBinary.setContacts(filteredList)
            } else {
                adapterBinary.setContacts(arrayListOf())
            }
        } else {
            adapterBinary.setContacts(contactList)
        }
    }
    private fun binarySearch(list: ArrayList<Contacts>, query: String): Int {
        var left = 0
        var right = list.size - 1

        while (left <= right) {
            val mid = (left + right) / 2
            val contact = list[mid]
            val fullName = "${list[mid].firstname} ${list[mid].surname}".lowercase(Locale.getDefault())

            when {
                fullName.contains(query) -> return mid
                contact.surname.lowercase(Locale.getDefault()).contains(query) -> return mid
                contact.email.lowercase(Locale.getDefault()).contains(query) -> return mid
                contact.phonenumber.contains(query) -> return mid
                contact.firstname.lowercase(Locale.getDefault()).compareTo(query) < 0 -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return -1
    }

    fun addData(){
        contactList = ArrayList()
        contactList.add(Contacts("Aether","Genshin","aether@genshin.com","09110203111"))
        contactList.add(Contacts("Beidou","Sea","beidou@genshin.com","09123874621"))
        contactList.add(Contacts("Jan Russel","Derpo","janrusselderpo@email.com","09956681249"))
        contactList.add(Contacts("Lermalyn","Buseo","lermalyn@email.com","09984473124"))
        contactList.add(Contacts("Mina","Myoui","myouimina@twice.com.kor","09952231239"))
        contactList.add(Contacts("Haerin","Kang","kanghaerin@ador.com.kor","09182349923"))
        contactList.add(Contacts("Minji","Kim","kimminji@ador.com.kor","09922341234"))
        contactList.add(Contacts("Hanni","Pham","phamhanni@ador.com.kor","09179874473"))
        contactList.sortBy { it.firstname }
    }
}