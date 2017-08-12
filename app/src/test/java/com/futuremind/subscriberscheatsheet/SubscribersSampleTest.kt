package com.futuremind.subscriberscheatsheet;

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)
class SubscribersSampleTest {

    lateinit var subscribersSample: SubscribersSample
    @Mock lateinit var listener: SubscribersSample.ThingsMaker

    @Before
    fun setUp() {
//        subscribersSample = SubscribersSample(listener)
//        val testSubscriber = subscribersSample.makeThings().test()
//        testSubscriber.awaitTerminalEvent(4, TimeUnit.SECONDS)
    }

    @Test
    fun `Completable wtf`(){
        Completable.complete()
                .andThen{ Completable.complete() }
                .test()
                .assertComplete()
//                .awaitTerminalEvent()
    }

//    @Test
//    fun `Peach is made on FRUITS scheduler`() = verify(listener, times(1)).makePeach(SubscribersSample.FRUITS)
//
//    @Test
//    fun `Banana is made on FRUITS scheduler`() = verify(listener, times(1)).makeBanana(SubscribersSample.FRUITS)
//
//    @Test
//    fun `Ant is made on INSECTS scheduler`() = verify(listener, times(1)).makeAnt(SubscribersSample.FRUITS)
//
//    @Test
//    fun `Ferrari is made on CARS scheduler`() = verify(listener, times(1)).makeFerrari(SubscribersSample.CARS)

}