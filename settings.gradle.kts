pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BNKK-Android"
include(":app")
include(":core:model")
include(":core:designsystem")
include(":feature:home")
include(":feature:explore")
include(":feature:create")
include(":feature:messages")
include(":feature:profile")
