package com.karoldm.phone_book

fun main() {
    val myPhoneBook = PhoneBook()

    myPhoneBook.addContact(name="Karol marques (me)", isFavorite = true)
    myPhoneBook.addContact(name="Ana Lucia", isFavorite = true, phone="199999999")
    myPhoneBook.addContact(name="Jessica marques", isFavorite = false, phone = "1999999999")

    println(myPhoneBook.listContacts())

    println(myPhoneBook.searchByName("K"))
    println(myPhoneBook.searchByName("k"))
    println(myPhoneBook.searchByName("y"))

    myPhoneBook.sendMessage("Olá!")
    myPhoneBook.sendMessage("Olá!", "Juliana")
}