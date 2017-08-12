package com.futuremind.subscriberscheatsheet

import io.reactivex.Completable

class SubscribersSample(val listener: WorkListener) {

    fun doTheWork(): Completable {

        val factory = NamedSchedulersFactory()

        return Completable.fromCallable { listener.workStarted(threadName()) }
                .subscribeOn(factory.getNamedScheduler("RED"))
                .observeOn(factory.getNamedScheduler("BLUE"))
                .doOnComplete { listener.workFinished(threadName()) }

    }

    private fun threadName() = Thread.currentThread().name

    interface WorkListener{
        fun workStarted(threadName: String)
        fun workFinished(threadName: String)
    }

}