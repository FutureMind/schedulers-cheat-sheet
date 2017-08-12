package com.futuremind.subscriberscheatsheet;

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SubscribersSampleTest {

    lateinit var subscribersSample: SubscribersSample
    @Mock lateinit var listener: SubscribersSample.WorkListener

    @Before
    fun setUp() {
        subscribersSample = SubscribersSample(listener)
    }

    @Test
    fun test(){

        val testSubscriber = subscribersSample.doTheWork().test()

        testSubscriber.awaitTerminalEvent()


        verify(listener, times(1)).workStarted("RED")
//        verify(listener, times(1)).workFinished("BLUE")

    }

}