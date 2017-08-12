package com.futuremind.subscriberscheatsheet

import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class NamedSchedulersFactory {

    fun scheduler(name: String) : Scheduler {
        val threadFactory = ThreadFactoryBuilder()
                .setNameFormat(name)
                .build()
        val es = Executors.newSingleThreadExecutor(threadFactory)
        return Schedulers.from(es)
    }

}