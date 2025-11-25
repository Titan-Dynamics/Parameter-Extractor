package com.ardupilot.paramextractor.model

import javafx.scene.paint.Color

enum class ParameterCategory(
    val displayName: String,
    val color: Color,
    val prefixes: List<String>
) {
    QUADPLANE(
        "QuadPlane (Q_)",
        Color.rgb(138, 43, 226),  // Blue Violet
        listOf("Q_")
    ),
    PID_ATTITUDE(
        "PID Controllers (Attitude)",
        Color.rgb(220, 20, 60),   // Crimson
        listOf("ATC_", "PTCH", "RLL", "YAW", "RATE_", "P_", "ACRO_")
    ),
    PID_POSITION(
        "PID Controllers (Position)",
        Color.rgb(255, 69, 0),    // Orange Red
        listOf("PSC_", "POS_", "VEL_", "LOIT_", "WPNAV_")
    ),
    TECS(
        "TECS (Energy Control)",
        Color.rgb(0, 128, 128),   // Teal
        listOf("TECS_", "PTCH2SRV_", "THR_")
    ),
    SERVOS(
        "Servos & Outputs",
        Color.rgb(34, 139, 34),   // Forest Green
        listOf("SERVO", "RC", "MOT_", "OUTPUT_")
    ),
    OSD(
        "On-Screen Display",
        Color.rgb(255, 215, 0),   // Gold
        listOf("OSD_", "OSD1_", "OSD2_", "OSD3_", "OSD4_", "OSD5_", "OSD6_")
    ),
    FAILSAFE(
        "Failsafe & Safety",
        Color.rgb(178, 34, 34),   // Firebrick
        listOf("FS_", "FLTMODE", "LAND_", "RTL_", "BATT_FS_", "FENCE_")
    ),
    SERIAL(
        "Serial Ports",
        Color.rgb(70, 130, 180),  // Steel Blue
        listOf("SERIAL")
    ),
    GPS(
        "GPS & Navigation",
        Color.rgb(0, 191, 255),   // Deep Sky Blue
        listOf("GPS_", "EK2_", "EK3_", "AHRS_", "COMPASS_", "MAG_")
    ),
    BATTERY(
        "Battery & Power",
        Color.rgb(255, 140, 0),   // Dark Orange
        listOf("BATT_", "BATT2_", "BATT3_")
    ),
    SENSORS(
        "Sensors",
        Color.rgb(148, 0, 211),   // Dark Violet
        listOf("INS_", "ARSPD_", "RNGFND_", "BARO_", "IMU_")
    ),
    CAMERA(
        "Camera & Gimbal",
        Color.rgb(255, 20, 147),  // Deep Pink
        listOf("CAM_", "MNT_")
    ),
    MISSION(
        "Mission & Auto",
        Color.rgb(30, 144, 255),  // Dodger Blue
        listOf("AUTO_", "WP_", "MIS_", "CIRCLE_", "GUIDED_")
    ),
    LOGGING(
        "Logging & Telemetry",
        Color.rgb(128, 128, 128), // Gray
        listOf("LOG_", "SR", "STAT_", "NTF_")
    ),
    RELAY(
        "Relays & Switches",
        Color.rgb(139, 69, 19),   // Saddle Brown
        listOf("RELAY_", "BTN_")
    ),
    SYSTEM(
        "System & Scheduler",
        Color.rgb(105, 105, 105), // Dim Gray
        listOf("SCHED_", "BRD_", "CAN_", "SIM_", "FORMAT_")
    ),
    TERRAIN(
        "Terrain Following",
        Color.rgb(154, 205, 50),  // Yellow Green
        listOf("TERRAIN_")
    ),
    AIRSPEED(
        "Airspeed Control",
        Color.rgb(100, 149, 237), // Cornflower Blue
        listOf("AIRSPEED_", "ARSPD", "ASPD_")
    ),
    OTHER(
        "Other Parameters",
        Color.rgb(169, 169, 169), // Dark Gray
        listOf()
    );

    companion object {
        fun fromParameterName(name: String): ParameterCategory {
            return values().firstOrNull { category ->
                category.prefixes.any { prefix ->
                    name.startsWith(prefix, ignoreCase = true)
                }
            } ?: OTHER
        }
    }
}
