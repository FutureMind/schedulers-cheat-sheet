package com.futuremind.subscriberscheatsheet

import io.reactivex.Flowable

class SubscribersSample {

    fun doTheWork(listener: WorkListener){

        val factory = NamedSchedulersFactory()

        Flowable.fromCallable { listener.workStarted(threadName()) }
                .subscribeOn(factory.getNamedScheduler("RED"))
                .subscribe({ listener.workFinished(threadName())})

    }

    private fun threadName() = Thread.currentThread().name

    interface WorkListener{
        fun workStarted(threadName: String)
        fun workFinished(threadName: String)
    }

}