package com.ardupilot.paramextractor.service

import com.ardupilot.paramextractor.model.Parameter
import com.ardupilot.paramextractor.model.ParameterCategory
import java.io.File

class ParameterParser {

    fun parseParameterFile(file: File): List<Parameter> {
        val parameters = mutableListOf<Parameter>()

        file.readLines().forEach { line ->
            val trimmedLine = line.trim()

            // Skip empty lines and comments
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                return@forEach
            }

            // Parse parameter line (format: PARAM_NAME,VALUE or PARAM_NAME VALUE)
            val parts = if (trimmedLine.contains(",")) {
                trimmedLine.split(",", limit = 2)
            } else {
                trimmedLine.split("\\s+".toRegex(), limit = 2)
            }

            if (parts.size >= 2) {
                val name = parts[0].trim()
                val value = parts[1].trim()
                val category = ParameterCategory.fromParameterName(name)
                parameters.add(Parameter(name, value, category))
            }
        }

        return parameters
    }

    fun exportParameters(parameters: List<Parameter>): String {
        return parameters.joinToString("\n") { it.toString() }
    }
}
