pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
<<<<<<< HEAD
=======
        maven { url = uri("https://jitpack.io") }
>>>>>>> f5a767b68cdc95727122243f10e71747c2e81105
    }
}

rootProject.name = "weitong_COMP304Lab3_Ex1"
include(":app")
 