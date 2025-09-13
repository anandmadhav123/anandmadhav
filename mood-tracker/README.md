# Mood Tracker (Android)

A simple, intuitive daily mood tracking app built with Kotlin, Jetpack Compose, Room, and Material 3. Pick your daily mood via emojis and view your trends with clean bar charts for the week or month.

## Features
- Emoji mood picker for today
- Persistent storage using Room
- Trends screen with week/month toggle and bar charts
- Material 3 theming with light/dark support

## Requirements
- Android Studio (Hedgehog or newer)
- Android SDK Platform 35
- JDK 17

## Getting Started
1. Open the project in Android Studio: `File > Open...` and choose the `mood-tracker` folder
2. Let Gradle sync
3. Select a device/emulator with API 26+
4. Run the `app` configuration

## Modules
- `app`: Android application module with UI, data, and navigation

## Tech Stack
- Kotlin, Coroutines
- Jetpack Compose, Material 3
- Room (SQLite)
- Navigation Compose

## Notes
- The app stores one mood per day; updating the same day overwrites the previous value.