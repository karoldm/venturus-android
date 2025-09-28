package com.karoldm.phone_book

import java.util.concurrent.locks.Condition

class PhoneBook {
    private val contacts = mutableListOf<Contact>();

    fun addContact(name: String, phone: String = "Sem número", isFavorite: Boolean) {
        contacts.add(Contact(name, phone, isFavorite));
    }

    fun listContacts(): List<String> {
       return filterContacts { it.isFavorite }.sortedBy { it.name }.map { it.name }
    }

    fun searchByName(name: String): List<Contact> {
        val query = name.lowercase()
        return filterContacts { it.name.lowercase().contains(query) }
    }

    fun filterContacts(condition: (Contact) -> Boolean): List<Contact> {
        return contacts.filter(condition)
    }

    fun sendMessage(message: String, sender: String = "Sistema") {
        println("Enviando mensagem: '$message' de $sender");
    }
}