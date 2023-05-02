package com.example.contacts_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ContactsAdapter(private val contactList: ArrayList<Contacts>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(),
    Filterable {

    private var filteredContactList: List<Contacts> = contactList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredContactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = filteredContactList[position]
        holder.firstName.text = contact.firstname
        holder.lastName.text = contact.surname
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.firstname)
        val lastName: TextView = itemView.findViewById(R.id.lastname)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.trim()?.lowercase(Locale.ROOT)

                filteredContactList = if (queryString.isNullOrEmpty()) {
                    contactList
                } else {

                    contactList.filter { contact ->
                        val firstNameMatch = contact.firstname.lowercase(Locale.ROOT).startsWith(queryString)
                        val surnameMatch = contact.surname.lowercase(Locale.ROOT).startsWith(queryString)
                        val emailMatch = contact.email.lowercase(Locale.ROOT).startsWith(queryString)
                        val phoneMatch = contact.phonenumber.lowercase(Locale.ROOT).startsWith(queryString)
                        firstNameMatch || surnameMatch || emailMatch || phoneMatch
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredContactList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredContactList = results?.values as ArrayList<Contacts>
                notifyDataSetChanged()
            }
        }
    }
}
