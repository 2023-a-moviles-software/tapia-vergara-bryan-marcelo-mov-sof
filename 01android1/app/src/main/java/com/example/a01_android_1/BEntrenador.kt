package com.example.a01_android_1

class BEntrenador (
    var id: Int,
    var nombre: String?,
    var descripcion: String?
){
    override fun toString(): String {
        return "$id - $nombre - $descripcion)"
    }
}