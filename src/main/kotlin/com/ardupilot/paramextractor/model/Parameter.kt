package com.ardupilot.paramextractor.model

data class Parameter(
    val name: String,
    val value: String,
    val category: ParameterCategory
) {
    override fun toString(): String = "$name,$value"
}
