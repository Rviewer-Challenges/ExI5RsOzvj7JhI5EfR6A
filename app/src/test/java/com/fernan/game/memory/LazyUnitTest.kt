package com.fernan.game.memory

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class LazyUnitTest {
    // `
    // dado el valor diferido cuando lo obtiene, entonces debería inicializarlo solo una vez
    @Test
    fun `givenLazyValue_whenGetIt_thenShouldInitializeItOnlyOnce`() {
        //given
        val numberOfInitializations = AtomicInteger()
        val lazyValue: ClassWithHeavyInitialization by lazy {
            numberOfInitializations.incrementAndGet()
            ClassWithHeavyInitialization()
        }
        //when
        println(lazyValue)
        println(lazyValue)

        //then
        assertEquals(numberOfInitializations.get(), 1)
    }

    /*
    se le dio un valor perezoso cuando se obtuvo usando la publicación y
    luego se pudo inicializar más de una vez

     */
    @Test
    fun givenLazyValue_whenGetItUsingPublication_thenCouldInitializeItMoreThanOnce() {
        //given
        val numberOfInitializations: AtomicInteger = AtomicInteger()
        val lazyValue: ClassWithHeavyInitialization by lazy(LazyThreadSafetyMode.PUBLICATION) {
            numberOfInitializations.incrementAndGet()
            ClassWithHeavyInitialization()
        }
        val executorService = Executors.newFixedThreadPool(8)
        val countDownLatch = CountDownLatch(1)
        //when
        for(i in 1..15){
            executorService.submit { countDownLatch.await(); println(lazyValue) }
            executorService.submit { countDownLatch.await(); println(lazyValue) }
        }

        // executorService.submit { countDownLatch.await(); println(lazyValue) }
        countDownLatch.countDown()

        //then
        executorService.shutdown()
        executorService.awaitTermination(5, TimeUnit.SECONDS)
        //assertEquals(numberOfInitializations.get(), 2)
    }

    class ClassWithHeavyInitialization {

    }


    lateinit var a: String
    @Test
    fun givenLateInitProperty_whenAccessItAfterInit_thenPass() {
        //when
        a = "it"
        println(a)

        //then not throw
    }

    @Test
    fun givenLateInitProperty_whenAccessItWithoutInit_thenThrow() {
        //when

    }
}