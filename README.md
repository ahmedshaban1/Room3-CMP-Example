# Room3 CMP Example

A Kotlin Multiplatform / Compose Multiplatform sample that lists products from a remote API and lets you favorite them, with favorites persisted locally via **Room3**. Targets Android, iOS, Desktop (JVM), and Web (Wasm/JS).

## Tech stack

- **Kotlin Multiplatform** + **Compose Multiplatform** — shared UI across all targets
- **Navigation 3** — type-safe navigation between list and detail screens
- **Ktor** — HTTP client (OkHttp on Android/JVM, Darwin on iOS, JS engine on Web)
- **kotlinx.serialization** — JSON parsing
- **Coil 3** — image loading
- **Koin** — dependency injection (`appModule` in `di/AppModule.kt`)
- **Room3 (KMP)** + **KSP** — local persistence for favorites
- **androidx.sqlite (bundled)** — SQLite driver shared across platforms
- **MVVM / MVI** — `ViewModel` exposing a `StateFlow<…State>` per screen

## Project layout

```
composeApp/src/
├── commonMain/kotlin/org/room3/exmple/
│   ├── App.kt                         # NavDisplay + Scaffold
│   ├── ProductListScreen.kt           # grid + favorite toggle
│   ├── data/
│   │   ├── local/                     # Room3 database, DAO, entity
│   │   ├── remote/                    # Ktor ProductApi
│   │   └── repository/                # Product + Favorite repos
│   ├── domain/                        # Product model, repository interfaces
│   ├── presentation/                  # ViewModels + UI state
│   └── di/AppModule.kt                # Koin wiring
├── androidMain/                       # MainActivity, DatabaseBuilder.android.kt
├── iosMain/                           # DatabaseBuilder.ios.kt
└── jvmMain/                           # DatabaseBuilder.jvm.kt
```

## How favorites persistence works

`FavoriteDao` exposes a `Flow<List<Int>>` of product IDs. `ProductListViewModel` `combine`s that flow into its `ProductListState.favoriteIds`, so tapping the heart on a grid item calls `toggleFavorite(productId)` → `FavoriteRepositoryImpl` → DAO insert/delete, and the UI updates reactively. The schema is exported to `composeApp/schemas/` (via `room3 { schemaDirectory(...) }`) for migration tracking.

The `AppDatabase` is declared in `commonMain` and constructed per-platform:

| Platform | DB location |
| --- | --- |
| Android | `context.getDatabasePath("favorites.db")` |
| iOS | `${NSHomeDirectory()}/Documents/favorites.db` |
| JVM (Desktop) | `~/.room3example/favorites.db` |

## Build & run

### Android

```shell
./gradlew :composeApp:assembleDebug
```

Or use the **composeApp** run configuration in Android Studio / IntelliJ.

### Desktop (JVM)

```shell
./gradlew :composeApp:run
```

### iOS

Open `iosApp/` in Xcode and run, or use the iOS run configuration in your IDE.

### Web

```shell
# Wasm (modern browsers)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# JS (older browsers)
./gradlew :composeApp:jsBrowserDevelopmentRun
```

> Note: the Web targets do not include Room3 — favorites persistence is wired for Android/iOS/JVM.

## Versions

Defined in `gradle/libs.versions.toml`:

- Kotlin / Compose Multiplatform — see `kotlin`, `composeMultiplatform`
- Room3 — `3.0.0-alpha02`
- KSP — `2.3.6`
- Ktor — `3.1.3`
- Koin — `4.0.4`
- Coil — `3.1.0`

## Learn more

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [Room KMP](https://developer.android.com/kotlin/multiplatform/room)