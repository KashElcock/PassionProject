package kash.passionproject.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import kash.passionproject.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BrewCardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrewCardDTO.class);
        BrewCardDTO brewCardDTO1 = new BrewCardDTO();
        brewCardDTO1.setId(1L);
        BrewCardDTO brewCardDTO2 = new BrewCardDTO();
        assertThat(brewCardDTO1).isNotEqualTo(brewCardDTO2);
        brewCardDTO2.setId(brewCardDTO1.getId());
        assertThat(brewCardDTO1).isEqualTo(brewCardDTO2);
        brewCardDTO2.setId(2L);
        assertThat(brewCardDTO1).isNotEqualTo(brewCardDTO2);
        brewCardDTO1.setId(null);
        assertThat(brewCardDTO1).isNotEqualTo(brewCardDTO2);
    }
}
