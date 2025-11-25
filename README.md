# Ardupilot Parameter Extractor

A JavaFX application built with Kotlin for filtering and extracting Ardupilot parameters by category.

## Features

- **Three-Pane Layout**:
  - **Left Pane**: Displays all parameters from the loaded file with color-coded rows based on parameter category
  - **Middle Pane**: Category selection checkboxes to filter parameters
  - **Right Pane**: Displays filtered parameters based on selected categories

- **Parameter Categories**:
  - QuadPlane (Q_) - Purple
  - PID Controllers (Attitude) - Crimson
  - PID Controllers (Position) - Orange Red
  - TECS (Energy Control) - Teal
  - Servos & Outputs - Forest Green
  - On-Screen Display - Gold
  - Failsafe & Safety - Firebrick
  - Serial Ports - Steel Blue
  - GPS & Navigation - Deep Sky Blue
  - Battery & Power - Dark Orange
  - Sensors - Dark Violet
  - Camera & Gimbal - Deep Pink
  - Mission & Auto - Dodger Blue
  - Logging & Telemetry - Gray
  - Relays & Switches - Saddle Brown
  - System & Scheduler - Dim Gray
  - Terrain Following - Yellow Green
  - Airspeed Control - Cornflower Blue
  - Other Parameters - Dark Gray

## Building and Running

### Prerequisites

- JDK 17 or higher
- Maven 3.6 or higher
- IntelliJ IDEA (recommended)

### Build with Maven

```bash
mvn clean compile
```

### Run the Application

```bash
mvn javafx:run
```

### Build JAR

```bash
mvn clean package
```

## Usage

1. **Open Parameter File**: Click the "Open Parameter File" button and select an Ardupilot parameter file (`.param`, `.parm`, or `.txt`)
2. **View Parameters**: All parameters will be displayed in the left pane with color-coding based on their category
3. **Filter Parameters**: Use the checkboxes in the middle pane to select which categories to include in the filtered output
4. **Export Filtered Parameters**: Click "Export Filtered Parameters" to save the filtered parameters to a new file

## Parameter File Format

The application supports Ardupilot parameter files in the following formats:

```
PARAM_NAME,VALUE
```

or

```
PARAM_NAME VALUE
```

Comments (lines starting with `#`) and empty lines are ignored.

## Sample File

A sample parameter file (`sample_parameters.param`) is included in the repository for testing purposes.

## Project Structure

```
src/main/kotlin/com/ardupilot/paramextractor/
├── MainApp.kt                      # Application entry point
├── model/
│   ├── Parameter.kt                # Parameter data class
│   └── ParameterCategory.kt        # Parameter category enum with colors
├── service/
│   └── ParameterParser.kt          # Parameter file parser
└── ui/
    ├── MainController.kt           # Main UI controller
    └── ParameterRow.kt             # Custom parameter row component
```

## IntelliJ IDEA Setup

1. Open the project in IntelliJ IDEA
2. Maven should auto-import dependencies
3. Run configuration:
   - Main class: `com.ardupilot.paramextractor.MainApp`
   - VM options: `--module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml`

Alternatively, use the Maven run configuration with goal: `javafx:run`
