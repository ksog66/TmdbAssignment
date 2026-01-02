# Mini Explorer - TheMovieDB KMP App

A Kotlin Multiplatform (KMP) application designed to demonstrate shared business logic across Android and iOS platforms. This app fetches data from **TheMovieDB API** to display a list of movies and detailed information, supporting offline access and clean UI states.

## 📱 Features

* [cite_start]**Cross-Platform:** Runs natively on Android and iOS using a single shared codebase[cite: 3].
* **Two-Screen Flow:**
    * [cite_start]**List Screen:** Displays a scrollable list of movies with search capability, handling loading, error, and empty states[cite: 10].
    * [cite_start]**Detail Screen:** Shows detailed information for a selected movie with meaningful fields[cite: 11].
* **Offline Support:** Caches successful API responses locally. [cite_start]If the network fails, it displays cached data with an indicator[cite: 13].
* [cite_start]**Pagination:** Supports loading data in chunks (Bonus Feature)[cite: 36].

## 🛠 Tech Stack

* **Language:** Kotlin (Multiplatform)
* [cite_start]**UI:** Compose Multiplatform (Android) & SwiftUI/Compose (iOS) [cite: 26]
* [cite_start]**Networking:** Ktor Client [cite: 21]
* [cite_start]**Serialization:** kotlinx.serialization [cite: 22]
* [cite_start]**Concurrency:** Coroutines and Flow [cite: 23]
* [cite_start]**Caching:** SQLDelight / Key-Value storage [cite: 24]
* **Dependency Injection:** Koin (implied by project structure)

---

## 🏗 Architecture

[cite_start]The project follows **Clean Architecture** principles to ensure separation of concerns and testability, keeping business logic within the shared KMP module[cite: 27].

### 1. Domain Layer (`domain`)
* **Role:** The core business logic, independent of any platform.
* **Components:**
    * [cite_start]**Models:** Pure Kotlin data classes (e.g., `Movie`)[cite: 15].
    * **UseCases:** Encapsulates business actions (e.g., `GetMovieDetailUseCase`).
    * [cite_start]**Repository Interfaces:** Defines contracts for data operations[cite: 16].

### 2. Data Layer (`data`)
* **Role:** Handles data retrieval from remote APIs or local cache.
* **Components:**
    * [cite_start]**Remote:** Ktor client implementation[cite: 15].
    * [cite_start]**Repository Implementation:** Manages data flow between remote and local sources[cite: 16].
    * **Mapper:** Converts network DTOs into Domain models.
    * **Paging:** Manages pagination logic.

### 3. Presentation Layer (`presentation`)
* **Role:** Handles UI rendering and state management.
* **Components:**
    * **Screens:** `Home`, `Detail`, `List`.
    * **Navigation:** Handles routing between screens.
    * [cite_start]**State:** Manages `Loading`, `Success`, and `Error` states [cite: 28-31].

---

## 🚀 Getting Started

To run this application, you must configure your API keys for both Android and iOS environments.

### 1. Prerequisites
* Android Studio (latest version recommended)
* Xcode (for running the iOS app)
* JDK 17 or higher
* An API Token from [TheMovieDB](https://www.themoviedb.org/documentation/api)

### 2. Android Configuration
To keep sensitive keys out of version control, we use a `keys.properties` file.

1.  Navigate to the `composeApp` directory.
2.  Create a new file named `keys.properties` as a sibling of `build.gradle.kts` (Module: composeApp).
3.  Add the following content:

```properties
baseUrl=api.themoviedb.org/3
bearerToken=YOUR_TOKEN_HERE
