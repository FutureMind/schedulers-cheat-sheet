package com.futuremind.subscriberscheatsheet;

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SchedulersTest {

    private val MAMMALS = "mammals scheduler"
    private val REPTILES = "reptiles scheduler"
    private val FISH = "fish scheduler"
    private val BIRDS = "birds scheduler"
    private val AMPHIBIANS = "amphibians (never used - overridden by MAMMALS)"

    private val schedulersFactory = NamedSchedulersFactory()
    @Mock private lateinit var animalsMaker: AnimalsMaker

    @Before
    fun setUp() {
        Completable.fromCallable { animalsMaker.makeCat(getThread()) }
                .doOnComplete { animalsMaker.makeDog(getThread()) }
                .observeOn(scheduler(FISH))
                .andThen(Completable.fromCallable { animalsMaker.makeShark(getThread()) })
                .andThen(
                        Completable.fromCallable { animalsMaker.makeChicken(getThread()) }
                                .subscribeOn(scheduler(BIRDS))
                                .andThen(Completable.fromCallable { animalsMaker.makeDuck(getThread()) })
                )
                .andThen(Completable.fromCallable { animalsMaker.makePenguin(getThread()) })
                .subscribeOn(scheduler(MAMMALS))
                .subscribeOn(scheduler(AMPHIBIANS))
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
    fun `Chicken is made on BIRDS scheduler`() = verify(animalsMaker, times(1)).makeChicken(BIRDS)

    @Test
    fun `Duck is made on BIRDS scheduler`() = verify(animalsMaker, times(1)).makeDuck(BIRDS)

    @Test
    fun `Penguin is made on BIRDS scheduler`() = verify(animalsMaker, times(1)).makePenguin(BIRDS)

    @Test
    fun `Crocodile is made on REPTILES scheduler`() = verify(animalsMaker, times(1)).makeCrocodile(REPTILES)

    @Test
    fun `No animals are made on AMPHIBIANS scheduler`() {
        verify(animalsMaker, never()).makeDog(AMPHIBIANS)
        verify(animalsMaker, never()).makeCat(AMPHIBIANS)
        verify(animalsMaker, never()).makeShark(AMPHIBIANS)
        verify(animalsMaker, never()).makeChicken(AMPHIBIANS)
        verify(animalsMaker, never()).makeDuck(AMPHIBIANS)
        verify(animalsMaker, never()).makePenguin(AMPHIBIANS)
        verify(animalsMaker, never()).makeCrocodile(AMPHIBIANS)
    }

    private fun getThread() = Thread.currentThread().name

    private fun scheduler(name: String) = schedulersFactory.getNamedScheduler(name)

}