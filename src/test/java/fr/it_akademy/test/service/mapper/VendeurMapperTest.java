package fr.it_akademy.test.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class VendeurMapperTest {

    private VendeurMapper vendeurMapper;

    @BeforeEach
    public void setUp() {
        vendeurMapper = new VendeurMapperImpl();
    }
}
