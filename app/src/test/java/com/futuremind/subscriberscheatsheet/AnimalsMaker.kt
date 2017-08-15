package com.futuremind.subscriberscheatsheet

interface AnimalsMaker {

    //mammals
    fun makeCat(threadName: String)
    fun makeDog(threadName: String)

    //fish
    fun makeShark(threadName: String)

    //birds
    fun makeDuck(threadName: String)
    fun makeChicken(threadName: String)
    fun makePenguin(threadName: String)

    //reptiles
    fun makeCrocodile(threadName: String)
}