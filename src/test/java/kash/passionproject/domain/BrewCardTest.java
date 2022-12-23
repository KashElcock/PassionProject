package kash.passionproject.domain;

import static org.assertj.core.api.Assertions.assertThat;

import kash.passionproject.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BrewCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrewCard.class);
        BrewCard brewCard1 = new BrewCard();
        brewCard1.setId(1L);
        BrewCard brewCard2 = new BrewCard();
        brewCard2.setId(brewCard1.getId());
        assertThat(brewCard1).isEqualTo(brewCard2);
        brewCard2.setId(2L);
        assertThat(brewCard1).isNotEqualTo(brewCard2);
        brewCard1.setId(null);
        assertThat(brewCard1).isNotEqualTo(brewCard2);
    }
}
