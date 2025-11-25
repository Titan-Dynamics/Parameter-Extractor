package com.ardupilot.paramextractor.ui

import com.ardupilot.paramextractor.model.Parameter
import javafx.scene.control.ListCell

class ParameterCell(
    private val onSelectionChanged: (Parameter, Boolean) -> Unit,
    private val showCheckbox: Boolean = true,
    private val selectedParameters: Set<Parameter>
) : ListCell<Parameter>() {

    private var row: ParameterRow? = null

    override fun updateItem(item: Parameter?, empty: Boolean) {
        super.updateItem(item, empty)

        if (empty || item == null) {
            graphic = null
            text = null
            row = null
        } else {
            if (row == null) {
                row = ParameterRow(item, onSelectionChanged, showCheckbox)
            }

            // Update checkbox state based on selectedParameters
            if (showCheckbox) {
                row?.checkBox?.isSelected = item in selectedParameters
            }

            graphic = row
            text = null
        }
    }
}
