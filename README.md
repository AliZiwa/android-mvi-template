# android-mvi-template

Provides a bootstrap template for Android projects using Compose UI, MVI pattern, Hilt, Retrofit, and Clean Architecture (Presentation, Domain, Data).

## Architecture

The project implements **MVI (Model-View-Intent)** combined with **Clean Architecture** in a single-module setup.

```
User Input (UiEvent) -> ViewModel.handleEvent() -> setState() -> UiState (UI re-renders)
                                                 -> sendEffect() -> UiEffect (one-time events)
```

### Project Structure

```
app/src/main/java/com/template/app/
├── core/                       # Shared infrastructure
│   ├── mvi/                    # MviViewModel base class, UiState, UiEvent, UiEffect
│   ├── navigation/             # Type-safe Screen routes (Kotlin Serialization)
│   ├── network/                # NetworkResult<T> sealed class
│   └── ui/theme/               # Material 3 theme
├── data/                       # Data layer
│   ├── local/                  # Room database, DAOs, DataStore
│   ├── remote/                 # Retrofit API services, DTOs
│   ├── repository/             # Repository implementations
│   └── mapper/                 # DTO <-> Domain model mappers
├── domain/                     # Business logic layer
│   ├── model/                  # Domain models (User, Friend)
│   ├── repository/             # Repository interfaces
│   └── usecase/                # Use cases (LoginUseCase, GetFriendsUseCase, etc.)
├── presentation/               # UI layer
│   ├── auth/                   # Login & Register screens
│   ├── home/                   # Home screen
│   ├── friends/detail/         # Friend detail screen
│   ├── settings/               # Settings screen
│   └── components/             # Reusable Compose components
├── di/                         # Hilt modules
├── worker/                     # WorkManager tasks
├── MainActivity.kt
└── TemplateApp.kt
```

### Layers

**Presentation** -- Each screen has a `Contract` (State, Event, Effect), a `ViewModel` extending `MviViewModel`, and a Compose screen function. Effects use a `Channel` to guarantee one-time delivery.

**Domain** -- Repository interfaces and single-responsibility use cases with `invoke()` operators. All business validation lives here.

**Data** -- Repository implementations, Retrofit API services, Room entities/DAOs, and DataStore for session management. Mappers handle conversion between DTOs, entities, and domain models.

### Dependency Injection (Hilt)

Six modules provide all dependencies:

| Module | Provides |
|--------|----------|
| `AppModule` | Coroutine dispatchers (IO, Default, Main) |
| `NetworkModule` | Retrofit, OkHttp, API services |
| `DatabaseModule` | Room database, DAOs |
| `DataStoreModule` | DataStore preferences |
| `RepositoryModule` | Repository interface bindings |
| `FirebaseModule` | Firebase Analytics |

### Navigation

Type-safe Compose Navigation using Kotlin Serialization for route definitions:

- `Login` -> `Home` (clears backstack)
- `Register` -> `Home` (clears backstack)
- `Home` -> `FriendDetail(friendId)` / `Settings`
- `Settings` -> Logout (full backstack clear)
- Deep links supported via `template://app` scheme

### Testing

- **Unit tests** -- JUnit 4, MockK, Turbine (Flow testing), Coroutines Test
- **UI tests** -- Compose UI Test with Hilt integration
- **Coverage** -- ViewModels, use cases, mappers, screens, and navigation flows

## Tech Stack

| Category | Library | Version |
|----------|---------|---------|
| UI | Jetpack Compose (Material 3) | BOM 2024.12.01 |
| Navigation | Compose Navigation | 2.8.5 |
| DI | Hilt | 2.53.1 |
| Network | Retrofit + OkHttp | 2.11.0 / 4.12.0 |
| Serialization | Kotlinx Serialization | 1.7.3 |
| Local Storage | Room | 2.6.1 |
| Preferences | DataStore | 1.1.1 |
| Background | WorkManager | 2.10.0 |
| Images | Coil | 2.7.0 |
| Async | Coroutines | 1.9.0 |
| Analytics | Firebase Analytics | 33.7.0 |

## Build Configuration

- **Kotlin**: 2.0.21 | **AGP**: 8.7.3 | **Java**: 17
- **Min SDK**: 26 | **Target/Compile SDK**: 35
- **Version Catalog**: `gradle/libs.versions.toml` for centralized dependency management
- **Release**: ProGuard minification and resource shrinking enabled
