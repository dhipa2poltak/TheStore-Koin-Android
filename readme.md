# TheStore-Koin-Android

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- ViewModel
- Coroutine
- Navigation Graph (Navigation Service Interface)
- Koin Dependency Injection
- ViewBinding
- Retrofit REST + OkHttp
- GSON Serialization
- Picasso Image Loader
- Unit Test: JUnit, Mockito, MockWebServer, Robolectric
- Code Coverage: JaCoCo
- All layouts are implemented with ConstraintLayout
- Gradle build flavors
- Proguard

**Caution:**
- **All the api calls are switching to IO dispatchers in DataSource class**
- **This project supports tablet device, it means when the app run on tablet device, the layout will display the master-detail layout. That's why feature_list module includes the feature_details module**

**To run Code Coverage (JaCoCo):**
1. Open Terminal then move to "root_project" directory.
2. type "./gradlew codeCoverModules allDebugCodeCoverage" (enter), wait until finish executing.

The report file will be located in "root_project/build/reports/jacoco/allDebugCoverage/html/index.html", open it with browser.

**Caution:**
**Later I will add more unit tests to increase the code coverage value.**
