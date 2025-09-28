package com.karoldm.phone_book

class Contact {

    var name: String = ""
        get() {
            if(isFavorite){
                return "$field ❤\uFE0F"
            }
            return field;
        }
        set(value) {
            if(value.trim().isEmpty()){
                println("O nome não pode ser vazio")
                return
            }
            field = value;
        }

    var phone: String = ""
        set(value) {
            field = if(value.trim().isEmpty()){
                "Sem número"
            } else {
                value
            }
        }

    var isFavorite: Boolean

    constructor(name: String, phone: String, isFavorite: Boolean){
        this.name = name
        this.phone = phone
        this.isFavorite = isFavorite
    }

    override fun toString(): String {
        return "Contact(name=$name, phone=$phone, isFavorite=$isFavorite)"
    }
}
