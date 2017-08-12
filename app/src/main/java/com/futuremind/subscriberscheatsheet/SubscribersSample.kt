package com.futuremind.subscriberscheatsheet

import io.reactivex.Completable

class SubscribersSample(val listener: WorkListener) {

    private val factory = NamedSchedulersFactory()

    companion object{
        val CARS = "cars scheduler"
        val FRUITS = "fruits scheduler"
        val OVERRIDDEN = "this one is never used, it's overridden by fruits"
    }

    fun doTheWork(): Completable {

        return Completable.fromCallable { listener.makeBanana(threadName()) }
                .doOnComplete { listener.makePeach(threadName()) }
                .subscribeOn(factory.scheduler(FRUITS))
                .subscribeOn(factory.scheduler(OVERRIDDEN))
                .observeOn(factory.scheduler(CARS))
                .doOnComplete { listener.makeFerrari(threadName()) }

    }

    private fun threadName() = Thread.currentThread().name

    interface WorkListener{
        fun makeBanana(threadName: String)
        fun makePeach(threadName: String)
        fun makeFerrari(threadName: String)
    }

}