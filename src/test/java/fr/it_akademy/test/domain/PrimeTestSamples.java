package fr.it_akademy.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrimeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prime getPrimeSample1() {
        return new Prime().id(1L).nomVendeur("nomVendeur1");
    }

    public static Prime getPrimeSample2() {
        return new Prime().id(2L).nomVendeur("nomVendeur2");
    }

    public static Prime getPrimeRandomSampleGenerator() {
        return new Prime().id(longCount.incrementAndGet()).nomVendeur(UUID.randomUUID().toString());
    }
}
