package com.ardupilot.paramextractor.ui

import com.ardupilot.paramextractor.model.Parameter
import com.ardupilot.paramextractor.model.ParameterCategory
import com.ardupilot.paramextractor.service.ParameterParser
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.util.Duration
import java.io.File
import java.net.URL
import java.util.*
import java.util.prefs.Preferences
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class MainViewController : Initializable {
    @FXML private lateinit var searchField: TextField
    @FXML private lateinit var leftPane: VBox
    @FXML private lateinit var categoryPane: VBox
    @FXML private lateinit var rightPane: VBox

    private val parameterParser = ParameterParser()
    private var allParameters = listOf<Parameter>()
    private val prefs = Preferences.userNodeForPackage(MainViewController::class.java)
    private var searchText = ""
    private var searchDebounceTask: CompletableFuture<Void>? = null
    private val categoryCheckBoxes = mutableMapOf<ParameterCategory, CheckBox>()

    var stage: Stage? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setupSearchListener()
    }

    private fun setupSearchListener() {
        searchField.textProperty().addListener { _, _, newValue ->
            debounceSearch(newValue)
        }
    }

    private fun debounceSearch(newValue: String) {
        // Cancel existing task
        searchDebounceTask?.cancel(true)

        // Create new debounced task
        searchDebounceTask = CompletableFuture.runAsync({
            Thread.sleep(1000)
        }).thenRun {
            javafx.application.Platform.runLater {
                searchText = newValue.lowercase()
                updateAllDisplays()
            }
        }
    }

    @FXML
    private fun onOpenFile() {
        val fileChooser = FileChooser().apply {
            title = "Open Parameter File"
            extensionFilters.addAll(
                FileChooser.ExtensionFilter("Parameter Files", "*.param", "*.parm", "*.txt"),
                FileChooser.ExtensionFilter("All Files", "*.*")
            )

            val lastDir = prefs.get("lastDirectory", null)
            if (lastDir != null) {
                val dir = File(lastDir)
                if (dir.exists() && dir.isDirectory) {
                    initialDirectory = dir
                }
            }
        }

        val file = fileChooser.showOpenDialog(stage)
        if (file != null) {
            prefs.put("lastDirectory", file.parent)
            loadParameters(file)
        }
    }

    @FXML
    private fun onSelectAll() {
        categoryCheckBoxes.values.forEach { it.isSelected = true }
        updateFilteredParameters()
    }

    @FXML
    private fun onDeselectAll() {
        categoryCheckBoxes.values.forEach { it.isSelected = false }
        updateFilteredParameters()
    }

    @FXML
    private fun onExport() {
        if (rightPane.children.isEmpty()) {
            showError("No Data", "There are no filtered parameters to export.")
            return
        }

        val fileChooser = FileChooser().apply {
            title = "Export Filtered Parameters"
            initialFileName = "filtered_parameters.param"
            extensionFilters.addAll(
                FileChooser.ExtensionFilter("Parameter Files", "*.param"),
                FileChooser.ExtensionFilter("Text Files", "*.txt"),
                FileChooser.ExtensionFilter("All Files", "*.*")
            )

            val lastDir = prefs.get("lastDirectory", null)
            if (lastDir != null) {
                val dir = File(lastDir)
                if (dir.exists() && dir.isDirectory) {
                    initialDirectory = dir
                }
            }
        }

        val file = fileChooser.showSaveDialog(stage)
        if (file != null) {
            try {
                prefs.put("lastDirectory", file.parent)

                val selectedCategories = categoryCheckBoxes.filter { it.value.isSelected }.keys
                val filteredParameters = allParameters.filter { it.category in selectedCategories }
                val content = parameterParser.exportParameters(filteredParameters)
                file.writeText(content)

                showInfo("Export Successful", "Filtered parameters exported successfully to:\n${file.absolutePath}")
            } catch (e: Exception) {
                showError("Export Failed", "Failed to export parameters: ${e.message}")
            }
        }
    }

    private fun loadParameters(file: File) {
        try {
            allParameters = parameterParser.parseParameterFile(file)
            updateCategoryCheckboxes()
            updateAllDisplays()
        } catch (e: Exception) {
            showError("Error loading file", "Failed to load parameter file: ${e.message}")
        }
    }

    private fun updateAllDisplays() {
        val filteredBySearch = if (searchText.isEmpty()) {
            allParameters
        } else {
            allParameters.filter {
                it.name.lowercase().contains(searchText) ||
                it.value.lowercase().contains(searchText)
            }
        }

        displayParameters(leftPane, filteredBySearch)
        updateFilteredParameters(filteredBySearch)
    }

    private fun updateCategoryCheckboxes() {
        val existingCategories = allParameters.map { it.category }.toSet()

        categoryPane.children.clear()
        categoryCheckBoxes.clear()

        ParameterCategory.values()
            .filter { it != ParameterCategory.OTHER && it in existingCategories }
            .forEach { category ->
                val checkBox = CheckBox(category.displayName).apply {
                    isSelected = true
                    setOnAction { updateFilteredParameters() }

                    val indicator = Rectangle(12.0, 12.0, category.color)
                    graphic = indicator
                }
                categoryCheckBoxes[category] = checkBox
                categoryPane.children.add(checkBox)
            }

        if (ParameterCategory.OTHER in existingCategories) {
            val otherCheckBox = CheckBox(ParameterCategory.OTHER.displayName).apply {
                isSelected = true
                setOnAction { updateFilteredParameters() }

                val indicator = Rectangle(12.0, 12.0, ParameterCategory.OTHER.color)
                graphic = indicator
            }
            categoryCheckBoxes[ParameterCategory.OTHER] = otherCheckBox
            categoryPane.children.add(otherCheckBox)
        }
    }

    private fun displayParameters(pane: VBox, parameters: List<Parameter>) {
        pane.children.clear()
        parameters.forEach { parameter ->
            pane.children.add(ParameterRow(parameter))
        }
    }

    private fun updateFilteredParameters(parametersToFilter: List<Parameter> = allParameters) {
        val selectedCategories = categoryCheckBoxes.filter { it.value.isSelected }.keys
        val filteredBySearch = if (searchText.isEmpty()) {
            parametersToFilter
        } else {
            parametersToFilter.filter {
                it.name.lowercase().contains(searchText) ||
                it.value.lowercase().contains(searchText)
            }
        }
        val filteredParameters = filteredBySearch.filter { it.category in selectedCategories }
        displayParameters(rightPane, filteredParameters)
    }

    private fun showError(title: String, message: String) {
        Alert(Alert.AlertType.ERROR).apply {
            this.title = title
            headerText = null
            contentText = message
            showAndWait()
        }
    }

    private fun showInfo(title: String, message: String) {
        Alert(Alert.AlertType.INFORMATION).apply {
            this.title = title
            headerText = null
            contentText = message
            showAndWait()
        }
    }
}
