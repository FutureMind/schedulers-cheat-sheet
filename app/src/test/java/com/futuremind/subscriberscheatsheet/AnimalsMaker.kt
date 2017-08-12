package com.futuremind.subscriberscheatsheet

interface AnimalsMaker {

    //mammals
    fun makeCat(threadName: String)
    fun makeDog(threadName: String)

    //fish
    fun makeShark(threadName: String)

    //birds
    fun makeHawk(threadName: String)
    fun makeSparrow(threadName: String)
    fun makeMockingbird(threadName: String)

    //reptiles
    fun makeCrocodile(threadName: String)
}