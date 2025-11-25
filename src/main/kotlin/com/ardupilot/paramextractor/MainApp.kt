package com.ardupilot.paramextractor

import com.ardupilot.paramextractor.ui.MainViewController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MainApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Ardupilot Parameter Extractor"

        // Load FXML
        val fxmlLoader = FXMLLoader(javaClass.getResource("/com/ardupilot/paramextractor/MainView.fxml"))
        val root = fxmlLoader.load<Parent>()

        // Get controller and set stage reference
        val controller = fxmlLoader.getController<MainViewController>()
        controller.stage = primaryStage

        val scene = Scene(root, 1460.0, 800.0)

        // Load dark theme CSS
        val cssResource = javaClass.getResource("/com/ardupilot/paramextractor/dark.css")
        if (cssResource != null) {
            scene.stylesheets.add(cssResource.toExternalForm())
        }

        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApp::class.java, *args)
        }
    }
}
