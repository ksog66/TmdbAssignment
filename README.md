# 🎬 Mini Explorer

A cross-platform movie exploration app built with **Kotlin Multiplatform (KMP)** that runs natively on **Android** and **iOS**.  
The app fetches movie data from **TheMovieDB API**, supports search and detailed views, and works **offline** using local caching.

---

## ✨ Features

- 📱 Runs on both **Android** and **iOS**
- 🔄 Shared business logic using **Kotlin Multiplatform**
- 🔍 Search movies and view detailed information
- 💾 Offline support with local database caching
- 🧼 Clean UI state handling
- 🏗️ Clean Architecture with clear separation of concerns

---

## 🛠 Tech Stack

- **Kotlin Multiplatform (KMP)**
- **Compose Multiplatform**
- **Ktor**
- **kotlinx.serialization**
- **Koin**
- **SQLDelight**
- **Coroutines & Flow**

---

## 🚀 Project Setup

### 1️⃣ Clone the Repository

```sh
git clone git@github.com:ksog66/TmdbAssignment.git
```

### 🤖 Android Setup

Navigate to the composeApp directory:

```sh
cd composeApp
```

Create a `keys.properties` file:

```sh
touch keys.properties
```

Add the following values to `keys.properties`:

```
baseUrl=api.themoviedb.org/3
bearerToken=YOUR_TOKEN_HERE
```

### 🍎 iOS Setup

1. Open the `iosApp` folder in Xcode
2. Open `Info.plist`
3. Add the following keys:
   - `BASE_URL=api.themoviedb.org/3`
   - `BEARER_TOKEN=YOUR_TOKEN_HERE`
4. Select the project root in Xcode
5. Go to **Signing & Capabilities**
6. Choose your Development Team

### ▶️ Run the App

1. Open the project in Android Studio or Xcode
2. Click **Run**

---

## 🧱 Architecture Used

This project follows **Clean Architecture**, ensuring scalability, testability, and maintainability.

### Presentation Layer
- Handles UI rendering and UI state
- Platform-specific UI (Compose for Android, SwiftUI for iOS)

### Domain Layer
- Contains business logic
- Uses UseCases for each action (e.g., fetch movies, movie details)
- Fully shared across platforms

### Data Layer
- Handles API communication and local caching
- Maps remote and local data models
- Shared across Android and iOS

The **Domain** and **Data** layers are shared between Android and iOS, eliminating duplicated logic.

---

https://github.com/user-attachments/assets/9baa0f64-3d05-43aa-94aa-54dd0b49eeb8



## 📌 Notes

- UseCases keep business logic clean and testable
- Offline-first approach improves user experience
- Designed to scale with additional features
