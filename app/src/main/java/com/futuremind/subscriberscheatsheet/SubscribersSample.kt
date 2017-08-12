package com.futuremind.subscriberscheatsheet

import io.reactivex.Flowable

class SubscribersSample(val listener: WorkListener) {

    fun doTheWork(){

        val factory = NamedSchedulersFactory()

        Flowable.fromCallable { listener.workStarted(threadName()) }
                .subscribeOn(factory.getNamedScheduler("RED"))
                .observeOn(factory.getNamedScheduler("BLUE"))
                .subscribe({ listener.workFinished(threadName())})

    }

    private fun threadName() = Thread.currentThread().name

    interface WorkListener{
        fun workStarted(threadName: String)
        fun workFinished(threadName: String)
    }

}