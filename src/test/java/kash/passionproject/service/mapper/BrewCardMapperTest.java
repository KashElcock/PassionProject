package kash.passionproject.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BrewCardMapperTest {

    private BrewCardMapper brewCardMapper;

    @BeforeEach
    public void setUp() {
        brewCardMapper = new BrewCardMapperImpl();
    }
}
