package com.example.a01_android_1

class ICities (
    public var name: String?,
    public var state: String?,
    public var country: String?,
    public var capital: Boolean?,
    public var population: Number?,
    public var regiones: List<String>?
) {
    override fun toString(): String {
        return "${name} - ${country}"
    }
}