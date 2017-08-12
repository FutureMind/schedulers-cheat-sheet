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

    private val MAMMALS = "mammals scheduler"
    private val REPTILES = "reptiles scheduler"
    private val FISH = "fish scheduler"
    private val OVERRIDDEN = "this one is never used, it's overridden by mammals scheduler"

    private val schedulersFactory = NamedSchedulersFactory()
    @Mock lateinit var animalsMaker: AnimalsMaker

    @Before
    fun setUp() {
        Completable.fromCallable { animalsMaker.makeCat(getThread()) }
                .doOnComplete { animalsMaker.makeDog(getThread()) }
                .observeOn(scheduler(FISH))
                .andThen(Completable.fromCallable { animalsMaker.makeShark(getThread()) })
                .subscribeOn(scheduler(MAMMALS))
                .subscribeOn(scheduler(OVERRIDDEN))
                .observeOn(scheduler(REPTILES))
                .doOnComplete { animalsMaker.makeCrocodile(getThread()) }
                .test()
                .awaitTerminalEvent()
    }

    @Test
    fun `Dog is made on MAMMALS scheduler`() = verify(animalsMaker, times(1)).makeDog(MAMMALS)

    @Test
    fun `Cat is made on MAMMALS scheduler`() = verify(animalsMaker, times(1)).makeCat(MAMMALS)

    @Test
    fun `Shark is made on FISH scheduler`() = verify(animalsMaker, times(1)).makeShark(FISH)

    @Test
    fun `Crocodile is made on REPTILES scheduler`() = verify(animalsMaker, times(1)).makeCrocodile(REPTILES)

    private fun getThread() = Thread.currentThread().name

    private fun scheduler(name: String) = schedulersFactory.getNamedScheduler(name)

}