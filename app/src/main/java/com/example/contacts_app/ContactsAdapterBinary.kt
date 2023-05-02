package com.example.contacts_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactsAdapterBinary(private var contactList: ArrayList<Contacts>) : RecyclerView.Adapter<ContactsAdapterBinary.ContactViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.firstName.text = contact.firstname
        holder.lastName.text = contact.surname
        holder.email.text = contact.email
        holder.phonenumber.text = contact.phonenumber
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.firstname)
        val lastName: TextView = itemView.findViewById(R.id.lastname)
        val email: TextView = itemView.findViewById(R.id.email)
        val phonenumber: TextView = itemView.findViewById(R.id.phonenumber)
    }

    fun setContacts(newContacts: ArrayList<Contacts>) {
        val startTime = System.nanoTime()
        contactList = newContacts
        notifyDataSetChanged()
        val endTime = System.nanoTime()
        val executionTime = endTime - startTime
        val executionTimeInMs = executionTime / 1_000_000.0
        Log.d("Search", "Execution time: ${executionTimeInMs}ms")
    }
}