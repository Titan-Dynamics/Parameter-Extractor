package com.ardupilot.paramextractor.ui

import com.ardupilot.paramextractor.model.Parameter
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class ParameterRow(parameter: Parameter) : HBox(8.0) {
    init {
        padding = Insets(4.0, 8.0, 4.0, 8.0)
        style = "-fx-background-color: ${toRgbString(parameter.category.color)};"

        // Color indicator
        val colorIndicator = Rectangle(4.0, 20.0, parameter.category.color)

        // Parameter name
        val nameLabel = Label(parameter.name).apply {
            style = "-fx-font-weight: bold; -fx-text-fill: white;"
            minWidth = 200.0
        }

        // Parameter value
        val valueLabel = Label(parameter.value).apply {
            style = "-fx-text-fill: white;"
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        children.addAll(colorIndicator, nameLabel, valueLabel)
    }

    private fun toRgbString(color: Color): String {
        val r = (color.red * 255).toInt()
        val g = (color.green * 255).toInt()
        val b = (color.blue * 255).toInt()
        return "rgb($r, $g, $b)"
    }
}
