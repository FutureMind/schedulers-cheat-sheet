package com.futuremind.subscriberscheatsheet;

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SubscribersTest {

    private val CARS = "cars scheduler"
    private val FRUITS = "fruits scheduler"
    private val INSECTS = "insects scheduler"
    private val OVERRIDDEN = "this one is never used, it's overridden by fruits scheduler"

    private val schedulersFactory = NamedSchedulersFactory()
    @Mock lateinit var thingsMaker: ThingsMaker

    @Before
    fun setUp() {
        Completable.fromCallable { thingsMaker.makeBanana(getThread()) }
                .doOnComplete { thingsMaker.makePeach(getThread()) }
                .observeOn(scheduler(INSECTS))
                .andThen(Completable.fromCallable { thingsMaker.makeAnt(getThread()) })
                .subscribeOn(scheduler(FRUITS))
                .subscribeOn(scheduler(OVERRIDDEN))
                .observeOn(scheduler(CARS))
                .doOnComplete { thingsMaker.makeFerrari(getThread()) }
                .test()
                .awaitTerminalEvent()
    }

    @Test
    fun `Peach is made on FRUITS scheduler`() = verify(thingsMaker, times(1)).makePeach(FRUITS)

    @Test
    fun `Banana is made on FRUITS scheduler`() = verify(thingsMaker, times(1)).makeBanana(FRUITS)

    @Test
    fun `Ant is made on INSECTS scheduler`() = verify(thingsMaker, times(1)).makeAnt(INSECTS)

    @Test
    fun `Ferrari is made on CARS scheduler`() = verify(thingsMaker, times(1)).makeFerrari(CARS)

    private fun getThread() = Thread.currentThread().name

    private fun scheduler(name: String) = schedulersFactory.getNamedScheduler(name)

}