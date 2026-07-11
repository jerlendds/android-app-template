# MobLab android-app-template

I use this template for vibe coding apps on my android phone (GrapheneOS), building the apps directly on my phone, and then installing them to the very same phone... lol

A MobLab Android 17 application template with a clean architecture baseline, MVP presentation pattern, reactive app buses, networking, persistence, debugging tools, and analytics hooks.

## Architecture

The project is split into three clean architecture modules plus the Android shell app:

- `:domain` contains framework-light business models, repository contracts, and use cases.
- `:data` contains Retrofit networking, Room persistence, repository implementations, RxRelay buses, and analytics implementations.
- `:presentation` contains MVP contracts, presenters, and Compose screens.
- `:app` wires the dependency graph, Android lifecycle entry points, debug tooling, theme, and manifest.

Dependency flow is intentionally one-way:

```text
app -> presentation -> domain
app -> data -> domain
```

`domain` does not depend on Android UI, Retrofit, Room, or app-level implementation details.

## Included Template Features

- Android 17 baseline using API `release(37)` for `compileSdk`, `minSdk`, and `targetSdk`.
- Kotlin and Jetpack Compose UI shell.
- Clean architecture module split: Data -> Domain -> Presentation.
- MVP presentation pattern through `BaseView`, `BasePresenter`, `HomeContract`, and `HomePresenter`.
- RxRelay `EventBus` for app-wide one-time events.
- RxRelay-backed `DataBus` and `AppDataStore` for keeping shared state synchronized.
- Retrofit networking with OkHttp logging and a sample GitHub users API.
- Room persistence with a sample `users` table, DAO, and database.
- Timber logging initialized from `MobLabApplication`.
- Stetho debug initialization and OkHttp interceptor in debug builds.
- LeakCanary dependency enabled for debug builds.
- `AnalyticsManager` interface with `MixpanelAnalyticsManager` and `NoOpAnalyticsManager` implementations.

## Sample Flow

The starter feature loads users through the full architecture stack:

```text
HomeScreen -> HomePresenter -> RefreshUsersUseCase -> UserRepository -> Retrofit + Room
```

The repository persists network results to Room, pushes synchronized state into `AppDataStore.users`, and posts refresh events on `EventBus`.

## Analytics

Analytics should be called through the `AnalyticsManager` interface instead of vendor SDKs directly. The app currently chooses `NoOpAnalyticsManager` unless `BuildConfig.MIXPANEL_TOKEN` is configured in `app/build.gradle.kts`.

```kotlin
analyticsManager.track(
    AnalyticsEvent(
        name = "screen_viewed",
        properties = mapOf("screen" to "home"),
    ),
)
```

## Debugging

Debug-only tooling lives behind `DebugTools` source-set implementations:

- `app/src/debug/.../DebugTools.kt` initializes Stetho and adds the Stetho OkHttp network interceptor.
- `app/src/release/.../DebugTools.kt` is a no-op implementation.

LeakCanary is included as a debug dependency and Timber is planted during application startup.

## Getting Started

1. Install a compatible JDK and Android SDK.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Configure any app-specific API base URLs, database entities, and analytics tokens.
5. Replace the sample GitHub user feature with the first MobLab product feature.

## Notes

- Package name: `com.jerlendds.moblab`
- App label: `MobLab`
- Room database: `moblab.db`
- Root project name: `MobLab`
