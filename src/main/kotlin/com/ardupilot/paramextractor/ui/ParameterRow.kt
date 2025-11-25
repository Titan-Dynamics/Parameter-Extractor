package com.ardupilot.paramextractor.ui

import com.ardupilot.paramextractor.model.Parameter
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.paint.Color

class ParameterRow(
    val parameter: Parameter,
    val onSelectionChanged: (Parameter, Boolean) -> Unit,
    val showCheckbox: Boolean = true
) : HBox(8.0) {
    val checkBox: CheckBox = CheckBox()

    init {
        padding = Insets(4.0, 8.0, 4.0, 8.0)
        alignment = Pos.CENTER_LEFT

        // Checkbox for selection (fixed width) - only if showCheckbox is true
        if (showCheckbox) {
            checkBox.apply {
                isSelected = true
                minWidth = 25.0
                maxWidth = 25.0
                setOnAction {
                    onSelectionChanged(parameter, isSelected)
                }
            }
            children.add(checkBox)
        }

        // Parameter name with colored text
        val nameLabel = Label(parameter.name).apply {
            style = "-fx-font-weight: bold; -fx-text-fill: ${toRgbString(parameter.category.color)};"
        }

        // Dotted line separator (grows to fill space)
        val separator = Region().apply {
            style = "-fx-border-style: dotted none none none; -fx-border-color: #555555; -fx-border-width: 0 0 1 0; -fx-translate-y: -3;"
            minHeight = 1.0
            maxHeight = 1.0
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        // Parameter value (right-aligned)
        val valueLabel = Label(parameter.value).apply {
            style = "-fx-text-fill: lightgray;"
        }

        children.addAll(nameLabel, separator, valueLabel)
    }

    private fun toRgbString(color: Color): String {
        val r = (color.red * 255).toInt()
        val g = (color.green * 255).toInt()
        val b = (color.blue * 255).toInt()
        return "rgb($r, $g, $b)"
    }
}
