# Rx Subscribers Cheat Sheet Demo

This project is the code behind a Future Mind's blog article (link soon).

The [SchedulersTest.kt](/app/src/test/java/com/futuremind/subscriberscheatsheet/SchedulersTest.kt) class is the one to look for. It contains a reactive chain and a set of tests to verify that specific operations (represented by *animals*) are made on appropriate schedulers (represented by *animal classes*).

It's written in Kotlin and uses RxJava 2.

## Building note

Sometimes Android Studio has problems with building projects with no Android dependencies. You may have to build this projects from command line (`gradle build`) to run the tests.
