package fr.it_akademy.test.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class PrimeMapperTest {

    private PrimeMapper primeMapper;

    @BeforeEach
    public void setUp() {
        primeMapper = new PrimeMapperImpl();
    }
}
