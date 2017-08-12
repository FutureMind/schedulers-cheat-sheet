package com.futuremind.subscriberscheatsheet

import io.reactivex.Completable
import io.reactivex.Flowable

class SubscribersSample(val thingsMaker: ThingsMaker) {

    private val schedulersFactory = NamedSchedulersFactory()

    companion object {
        val CARS = "cars scheduler"
        val FRUITS = "fruits scheduler"
        val INSECTS = "insects scheduler"
        val OVERRIDDEN = "this one is never used, it's overridden by fruits scheduler"
    }

    fun makeThings(): Completable {

        return Completable.fromCallable { thingsMaker.makeBanana(getThread()) }
                .doOnComplete { thingsMaker.makePeach(getThread()) }
                .observeOn(scheduler(INSECTS))
                .andThen(Completable.fromCallable { thingsMaker.makeAnt(getThread()) })
                .subscribeOn(scheduler(FRUITS))
                .subscribeOn(scheduler(OVERRIDDEN))
                .observeOn(scheduler(CARS))
                .doOnComplete { thingsMaker.makeFerrari(getThread()) }

    }

    private fun getThread() = Thread.currentThread().name

    private fun scheduler(name: String) = schedulersFactory.getNamedScheduler(name)

    interface ThingsMaker {
        fun makeBanana(threadName: String)
        fun makePeach(threadName: String)
        fun makeAnt(threadName: String)
        fun makeFerrari(threadName: String)
    }

}