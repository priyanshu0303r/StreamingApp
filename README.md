# StreamingApp

**Android Real-Time Streaming & Perplexity Calculator App**

This project demonstrates an Android application featuring real-time data streaming using WebSockets, an AI/NLP perplexity calculator, and offline caching via Room. The app is built with Kotlin, uses Jetpack Compose for the UI, follows the MVVM architecture, and leverages Hilt for dependency injection.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [How to Run](#how-to-run)
- [Testing](#testing)

---

## Overview

StreamingApp is a feature-rich Android project that:
- **Streams real-time data** using a WebSocket connection.
- **Calculates perplexity** based on user-input probabilities in real time.
- **Caches streaming data** locally using Room for offline access.

The application is built using modern Android development practices and libraries, including Jetpack Compose for UI, Kotlin Coroutines for asynchronous handling, and Hilt for dependency injection.

---

## Features

- **Real-Time Streaming:**
  - Connects to a WebSocket endpoint (e.g., `wss://echo.websocket.events`) to receive live data.
  - Displays live stream data in a unified list along with locally cached messages.
  - Provides an input field to send echo messages to the server.

- **Perplexity Calculator:**
  - Users enter a sequence of comma-separated probabilities.
  - Calculates and displays perplexity in real time using the formula:
    \[
    \text{Perplexity} = \exp\left(-\frac{1}{N}\sum \ln(p_i)\right)
    \]
  - Clear, step-by-step instructions with emotion icons guide the user.

- **Offline Caching:**
  - Incoming streaming data is saved locally using Room.
  - Cached data is displayed alongside live data for user review.

---

## Architecture

- **MVVM (Model-View-ViewModel):**
  - **Model:** Data classes (`StreamData`, `StreamingDataEntity`) represent the data.
  - **ViewModel:** `StreamingViewModel` and `PerplexityViewModel` encapsulate business logic and expose state via Kotlin `StateFlow`.
  - **View:** Jetpack Compose screens (`StreamingScreen` & `PerplexityScreen`) provide the UI.

- **Dependency Injection with Hilt:**
  - Hilt modules manage dependencies like `OkHttpClient`, WebSocket, and Room database.

- **Networking:**
  - Uses OkHttp to create and manage WebSocket connections.

- **Offline Storage:**
  - Implements Room for caching streaming data locally.

---

## How to Run

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/priyanshu0303r/StreamingApp.git
   cd StreamingApp
## Open in Android Studio:
- Open Android Studio.
- Select **Open an Existing Project** and navigate to the cloned repository.

## Build & Run:
- Ensure your emulator or device is connected.
- Click the **Run** button in Android Studio.

## Verify Permissions:
- Make sure the following permission is declared in the `AndroidManifest.xml`:
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />


## Testing the App:

### Streaming Screen:
- The app automatically connects to the WebSocket.
- Use the input field to send messages and view live as well as cached data.

### Perplexity Screen:
- Enter comma-separated probabilities (e.g., `0.1, 0.2, 0.3`) and observe real-time perplexity calculation.

---

## Testing:

- The project includes test cases to verify key functionalities:

  - **StreamingViewModelTest:**
    - Validates that live streaming data is collected correctly.
    - Verifies that sending messages via the view model triggers the correct repository functionality.

- **Testing Tools:**
  - JUnit
  - `kotlinx-coroutines-test`


https://github.com/user-attachments/assets/15d91813-29ac-4306-bb0e-9eb98a4730b4



