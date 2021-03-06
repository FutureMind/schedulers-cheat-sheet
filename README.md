# Rx Schedulers Cheat Sheet Demo

This project is the code behind a Future Mind's [blog article](https://www.futuremind.com/blog/rxjava-schedulers-cheat-sheet).

The [SchedulersTest.kt](/app/src/test/java/com/futuremind/subscriberscheatsheet/SchedulersTest.kt) class is the one to look for. It contains a reactive chain and a set of tests to verify that specific operations (represented by *animals*) are made on appropriate schedulers (represented by *animal classes*).

It's written in Kotlin and uses RxJava 2.

## The cheat sheet

You're not in the mood of running any tests and would rather just grab a pretty, colorful PDF, frame it and put over the fireplace?

[Say no more.](/schedulers-cheat-sheet.pdf)

## Building note

Sometimes Android Studio has problems with building pure Kotlin projects (no Android dependencies). You may have to build this project from command line (`gradle build`) to run the tests.
