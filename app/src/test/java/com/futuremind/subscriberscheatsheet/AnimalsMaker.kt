package com.futuremind.subscriberscheatsheet

interface AnimalsMaker {
    fun makeCat(threadName: String)
    fun makeDog(threadName: String)
    fun makeShark(threadName: String)
    fun makeCrocodile(threadName: String)
}