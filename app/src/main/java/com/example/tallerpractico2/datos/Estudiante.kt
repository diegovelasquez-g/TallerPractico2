package com.example.tallerpractico2.datos

class Estudiante {
    fun key(key: String?) {
    }
    var nombre: String? = null
    var nota1: String? = null
    var nota2: String? = null
    var nota3: String? = null
    var nota4: String? = null
    var nota5: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()
    constructor() {}
    constructor(nombre: String?, nota1: String?, nota2: String?, nota3: String?, nota4: String?, nota5: String?) {
        this.nombre = nombre
        this.nota1 = nota1
        this.nota2 = nota2
        this.nota3 = nota3
        this.nota4 = nota4
        this.nota5 = nota5
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "nota1" to nota1,
            "nota2" to nota2,
            "nota3" to nota3,
            "nota4" to nota4,
            "nota5" to nota5,
            "key" to key,
            "per" to per
        )
    }
}