package com.example.tallerpractico2.datos

class Empleado {
    fun key(key: String?) {
    }
    var nombre: String? = null
    var salario: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()
    constructor() {}
    constructor(nombre: String?, salario: String?) {
        this.nombre = nombre
        this.salario = salario
    }
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "salario" to salario,
            "key" to key,
            "per" to per
        )
    }
}