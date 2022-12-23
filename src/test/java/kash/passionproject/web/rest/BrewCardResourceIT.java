package kash.passionproject.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import kash.passionproject.IntegrationTest;
import kash.passionproject.domain.BrewCard;
import kash.passionproject.domain.enumeration.BrewMethod;
import kash.passionproject.domain.enumeration.CoffeeRegion;
import kash.passionproject.domain.enumeration.CoffeeSubregion;
import kash.passionproject.domain.enumeration.CoffeeType;
import kash.passionproject.domain.enumeration.FlavorProfile;
import kash.passionproject.domain.enumeration.ProcessingMethod;
import kash.passionproject.domain.enumeration.RoastLevel;
import kash.passionproject.repository.BrewCardRepository;
import kash.passionproject.service.BrewCardService;
import kash.passionproject.service.dto.BrewCardDTO;
import kash.passionproject.service.mapper.BrewCardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BrewCardResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BrewCardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BrewMethod DEFAULT_BREW_METHOD = BrewMethod.POUR_OVER;
    private static final BrewMethod UPDATED_BREW_METHOD = BrewMethod.FRENCH_PRESS;

    private static final CoffeeType DEFAULT_COFFEE_TYPE = CoffeeType.ARABICA;
    private static final CoffeeType UPDATED_COFFEE_TYPE = CoffeeType.ROBUSTA;

    private static final CoffeeRegion DEFAULT_COFFEE_REGION = CoffeeRegion.AFRICA;
    private static final CoffeeRegion UPDATED_COFFEE_REGION = CoffeeRegion.ASIA;

    private static final CoffeeSubregion DEFAULT_COFFEE_SUBREGION = CoffeeSubregion.ETHIOPIA;
    private static final CoffeeSubregion UPDATED_COFFEE_SUBREGION = CoffeeSubregion.KENYA;

    private static final RoastLevel DEFAULT_ROAST_LEVEL = RoastLevel.LIGHT;
    private static final RoastLevel UPDATED_ROAST_LEVEL = RoastLevel.MEDIUM;

    private static final ProcessingMethod DEFAULT_PROCESSING_METHOD = ProcessingMethod.WASHED;
    private static final ProcessingMethod UPDATED_PROCESSING_METHOD = ProcessingMethod.NATURAL;

    private static final FlavorProfile DEFAULT_FLAVOR_PROFILE = FlavorProfile.FRUITY;
    private static final FlavorProfile UPDATED_FLAVOR_PROFILE = FlavorProfile.CHOCOLATY;

    private static final Double DEFAULT_COFFEE_WEIGHT = 0D;
    private static final Double UPDATED_COFFEE_WEIGHT = 1D;

    private static final Double DEFAULT_WATER_WEIGHT = 0D;
    private static final Double UPDATED_WATER_WEIGHT = 1D;

    private static final Double DEFAULT_WATER_TEMP = 0D;
    private static final Double UPDATED_WATER_TEMP = 1D;

    private static final Integer DEFAULT_BREW_TIME = 0;
    private static final Integer UPDATED_BREW_TIME = 1;

    private static final LocalDate DEFAULT_BREW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BREW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EQUIPMENT = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/brew-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BrewCardRepository brewCardRepository;

    @Mock
    private BrewCardRepository brewCardRepositoryMock;

    @Autowired
    private BrewCardMapper brewCardMapper;

    @Mock
    private BrewCardService brewCardServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrewCardMockMvc;

    private BrewCard brewCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrewCard createEntity(EntityManager em) {
        BrewCard brewCard = new BrewCard()
            .name(DEFAULT_NAME)
            .brewMethod(DEFAULT_BREW_METHOD)
            .coffeeType(DEFAULT_COFFEE_TYPE)
            .coffeeRegion(DEFAULT_COFFEE_REGION)
            .coffeeSubregion(DEFAULT_COFFEE_SUBREGION)
            .roastLevel(DEFAULT_ROAST_LEVEL)
            .processingMethod(DEFAULT_PROCESSING_METHOD)
            .flavorProfile(DEFAULT_FLAVOR_PROFILE)
            .coffeeWeight(DEFAULT_COFFEE_WEIGHT)
            .waterWeight(DEFAULT_WATER_WEIGHT)
            .waterTemp(DEFAULT_WATER_TEMP)
            .brewTime(DEFAULT_BREW_TIME)
            .brewDate(DEFAULT_BREW_DATE)
            .equipment(DEFAULT_EQUIPMENT)
            .notes(DEFAULT_NOTES)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        return brewCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BrewCard createUpdatedEntity(EntityManager em) {
        BrewCard brewCard = new BrewCard()
            .name(UPDATED_NAME)
            .brewMethod(UPDATED_BREW_METHOD)
            .coffeeType(UPDATED_COFFEE_TYPE)
            .coffeeRegion(UPDATED_COFFEE_REGION)
            .coffeeSubregion(UPDATED_COFFEE_SUBREGION)
            .roastLevel(UPDATED_ROAST_LEVEL)
            .processingMethod(UPDATED_PROCESSING_METHOD)
            .flavorProfile(UPDATED_FLAVOR_PROFILE)
            .coffeeWeight(UPDATED_COFFEE_WEIGHT)
            .waterWeight(UPDATED_WATER_WEIGHT)
            .waterTemp(UPDATED_WATER_TEMP)
            .brewTime(UPDATED_BREW_TIME)
            .brewDate(UPDATED_BREW_DATE)
            .equipment(UPDATED_EQUIPMENT)
            .notes(UPDATED_NOTES)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        return brewCard;
    }

    @BeforeEach
    public void initTest() {
        brewCard = createEntity(em);
    }

    @Test
    @Transactional
    void createBrewCard() throws Exception {
        int databaseSizeBeforeCreate = brewCardRepository.findAll().size();
        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);
        restBrewCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brewCardDTO)))
            .andExpect(status().isCreated());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeCreate + 1);
        BrewCard testBrewCard = brewCardList.get(brewCardList.size() - 1);
        assertThat(testBrewCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBrewCard.getBrewMethod()).isEqualTo(DEFAULT_BREW_METHOD);
        assertThat(testBrewCard.getCoffeeType()).isEqualTo(DEFAULT_COFFEE_TYPE);
        assertThat(testBrewCard.getCoffeeRegion()).isEqualTo(DEFAULT_COFFEE_REGION);
        assertThat(testBrewCard.getCoffeeSubregion()).isEqualTo(DEFAULT_COFFEE_SUBREGION);
        assertThat(testBrewCard.getRoastLevel()).isEqualTo(DEFAULT_ROAST_LEVEL);
        assertThat(testBrewCard.getProcessingMethod()).isEqualTo(DEFAULT_PROCESSING_METHOD);
        assertThat(testBrewCard.getFlavorProfile()).isEqualTo(DEFAULT_FLAVOR_PROFILE);
        assertThat(testBrewCard.getCoffeeWeight()).isEqualTo(DEFAULT_COFFEE_WEIGHT);
        assertThat(testBrewCard.getWaterWeight()).isEqualTo(DEFAULT_WATER_WEIGHT);
        assertThat(testBrewCard.getWaterTemp()).isEqualTo(DEFAULT_WATER_TEMP);
        assertThat(testBrewCard.getBrewTime()).isEqualTo(DEFAULT_BREW_TIME);
        assertThat(testBrewCard.getBrewDate()).isEqualTo(DEFAULT_BREW_DATE);
        assertThat(testBrewCard.getEquipment()).isEqualTo(DEFAULT_EQUIPMENT);
        assertThat(testBrewCard.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testBrewCard.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testBrewCard.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createBrewCardWithExistingId() throws Exception {
        // Create the BrewCard with an existing ID
        brewCard.setId(1L);
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        int databaseSizeBeforeCreate = brewCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrewCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brewCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBrewCards() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        // Get all the brewCardList
        restBrewCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brewCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].brewMethod").value(hasItem(DEFAULT_BREW_METHOD.toString())))
            .andExpect(jsonPath("$.[*].coffeeType").value(hasItem(DEFAULT_COFFEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].coffeeRegion").value(hasItem(DEFAULT_COFFEE_REGION.toString())))
            .andExpect(jsonPath("$.[*].coffeeSubregion").value(hasItem(DEFAULT_COFFEE_SUBREGION.toString())))
            .andExpect(jsonPath("$.[*].roastLevel").value(hasItem(DEFAULT_ROAST_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].processingMethod").value(hasItem(DEFAULT_PROCESSING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].flavorProfile").value(hasItem(DEFAULT_FLAVOR_PROFILE.toString())))
            .andExpect(jsonPath("$.[*].coffeeWeight").value(hasItem(DEFAULT_COFFEE_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].waterWeight").value(hasItem(DEFAULT_WATER_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].waterTemp").value(hasItem(DEFAULT_WATER_TEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].brewTime").value(hasItem(DEFAULT_BREW_TIME)))
            .andExpect(jsonPath("$.[*].brewDate").value(hasItem(DEFAULT_BREW_DATE.toString())))
            .andExpect(jsonPath("$.[*].equipment").value(hasItem(DEFAULT_EQUIPMENT)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBrewCardsWithEagerRelationshipsIsEnabled() throws Exception {
        when(brewCardServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBrewCardMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(brewCardServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBrewCardsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(brewCardServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBrewCardMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(brewCardRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBrewCard() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        // Get the brewCard
        restBrewCardMockMvc
            .perform(get(ENTITY_API_URL_ID, brewCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brewCard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.brewMethod").value(DEFAULT_BREW_METHOD.toString()))
            .andExpect(jsonPath("$.coffeeType").value(DEFAULT_COFFEE_TYPE.toString()))
            .andExpect(jsonPath("$.coffeeRegion").value(DEFAULT_COFFEE_REGION.toString()))
            .andExpect(jsonPath("$.coffeeSubregion").value(DEFAULT_COFFEE_SUBREGION.toString()))
            .andExpect(jsonPath("$.roastLevel").value(DEFAULT_ROAST_LEVEL.toString()))
            .andExpect(jsonPath("$.processingMethod").value(DEFAULT_PROCESSING_METHOD.toString()))
            .andExpect(jsonPath("$.flavorProfile").value(DEFAULT_FLAVOR_PROFILE.toString()))
            .andExpect(jsonPath("$.coffeeWeight").value(DEFAULT_COFFEE_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.waterWeight").value(DEFAULT_WATER_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.waterTemp").value(DEFAULT_WATER_TEMP.doubleValue()))
            .andExpect(jsonPath("$.brewTime").value(DEFAULT_BREW_TIME))
            .andExpect(jsonPath("$.brewDate").value(DEFAULT_BREW_DATE.toString()))
            .andExpect(jsonPath("$.equipment").value(DEFAULT_EQUIPMENT))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }

    @Test
    @Transactional
    void getNonExistingBrewCard() throws Exception {
        // Get the brewCard
        restBrewCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrewCard() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();

        // Update the brewCard
        BrewCard updatedBrewCard = brewCardRepository.findById(brewCard.getId()).get();
        // Disconnect from session so that the updates on updatedBrewCard are not directly saved in db
        em.detach(updatedBrewCard);
        updatedBrewCard
            .name(UPDATED_NAME)
            .brewMethod(UPDATED_BREW_METHOD)
            .coffeeType(UPDATED_COFFEE_TYPE)
            .coffeeRegion(UPDATED_COFFEE_REGION)
            .coffeeSubregion(UPDATED_COFFEE_SUBREGION)
            .roastLevel(UPDATED_ROAST_LEVEL)
            .processingMethod(UPDATED_PROCESSING_METHOD)
            .flavorProfile(UPDATED_FLAVOR_PROFILE)
            .coffeeWeight(UPDATED_COFFEE_WEIGHT)
            .waterWeight(UPDATED_WATER_WEIGHT)
            .waterTemp(UPDATED_WATER_TEMP)
            .brewTime(UPDATED_BREW_TIME)
            .brewDate(UPDATED_BREW_DATE)
            .equipment(UPDATED_EQUIPMENT)
            .notes(UPDATED_NOTES)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(updatedBrewCard);

        restBrewCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brewCardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isOk());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
        BrewCard testBrewCard = brewCardList.get(brewCardList.size() - 1);
        assertThat(testBrewCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrewCard.getBrewMethod()).isEqualTo(UPDATED_BREW_METHOD);
        assertThat(testBrewCard.getCoffeeType()).isEqualTo(UPDATED_COFFEE_TYPE);
        assertThat(testBrewCard.getCoffeeRegion()).isEqualTo(UPDATED_COFFEE_REGION);
        assertThat(testBrewCard.getCoffeeSubregion()).isEqualTo(UPDATED_COFFEE_SUBREGION);
        assertThat(testBrewCard.getRoastLevel()).isEqualTo(UPDATED_ROAST_LEVEL);
        assertThat(testBrewCard.getProcessingMethod()).isEqualTo(UPDATED_PROCESSING_METHOD);
        assertThat(testBrewCard.getFlavorProfile()).isEqualTo(UPDATED_FLAVOR_PROFILE);
        assertThat(testBrewCard.getCoffeeWeight()).isEqualTo(UPDATED_COFFEE_WEIGHT);
        assertThat(testBrewCard.getWaterWeight()).isEqualTo(UPDATED_WATER_WEIGHT);
        assertThat(testBrewCard.getWaterTemp()).isEqualTo(UPDATED_WATER_TEMP);
        assertThat(testBrewCard.getBrewTime()).isEqualTo(UPDATED_BREW_TIME);
        assertThat(testBrewCard.getBrewDate()).isEqualTo(UPDATED_BREW_DATE);
        assertThat(testBrewCard.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testBrewCard.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBrewCard.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testBrewCard.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brewCardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brewCardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrewCardWithPatch() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();

        // Update the brewCard using partial update
        BrewCard partialUpdatedBrewCard = new BrewCard();
        partialUpdatedBrewCard.setId(brewCard.getId());

        partialUpdatedBrewCard
            .name(UPDATED_NAME)
            .brewMethod(UPDATED_BREW_METHOD)
            .coffeeRegion(UPDATED_COFFEE_REGION)
            .coffeeSubregion(UPDATED_COFFEE_SUBREGION)
            .roastLevel(UPDATED_ROAST_LEVEL)
            .flavorProfile(UPDATED_FLAVOR_PROFILE)
            .coffeeWeight(UPDATED_COFFEE_WEIGHT)
            .waterWeight(UPDATED_WATER_WEIGHT)
            .brewDate(UPDATED_BREW_DATE)
            .equipment(UPDATED_EQUIPMENT)
            .notes(UPDATED_NOTES);

        restBrewCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrewCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBrewCard))
            )
            .andExpect(status().isOk());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
        BrewCard testBrewCard = brewCardList.get(brewCardList.size() - 1);
        assertThat(testBrewCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrewCard.getBrewMethod()).isEqualTo(UPDATED_BREW_METHOD);
        assertThat(testBrewCard.getCoffeeType()).isEqualTo(DEFAULT_COFFEE_TYPE);
        assertThat(testBrewCard.getCoffeeRegion()).isEqualTo(UPDATED_COFFEE_REGION);
        assertThat(testBrewCard.getCoffeeSubregion()).isEqualTo(UPDATED_COFFEE_SUBREGION);
        assertThat(testBrewCard.getRoastLevel()).isEqualTo(UPDATED_ROAST_LEVEL);
        assertThat(testBrewCard.getProcessingMethod()).isEqualTo(DEFAULT_PROCESSING_METHOD);
        assertThat(testBrewCard.getFlavorProfile()).isEqualTo(UPDATED_FLAVOR_PROFILE);
        assertThat(testBrewCard.getCoffeeWeight()).isEqualTo(UPDATED_COFFEE_WEIGHT);
        assertThat(testBrewCard.getWaterWeight()).isEqualTo(UPDATED_WATER_WEIGHT);
        assertThat(testBrewCard.getWaterTemp()).isEqualTo(DEFAULT_WATER_TEMP);
        assertThat(testBrewCard.getBrewTime()).isEqualTo(DEFAULT_BREW_TIME);
        assertThat(testBrewCard.getBrewDate()).isEqualTo(UPDATED_BREW_DATE);
        assertThat(testBrewCard.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testBrewCard.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBrewCard.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testBrewCard.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateBrewCardWithPatch() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();

        // Update the brewCard using partial update
        BrewCard partialUpdatedBrewCard = new BrewCard();
        partialUpdatedBrewCard.setId(brewCard.getId());

        partialUpdatedBrewCard
            .name(UPDATED_NAME)
            .brewMethod(UPDATED_BREW_METHOD)
            .coffeeType(UPDATED_COFFEE_TYPE)
            .coffeeRegion(UPDATED_COFFEE_REGION)
            .coffeeSubregion(UPDATED_COFFEE_SUBREGION)
            .roastLevel(UPDATED_ROAST_LEVEL)
            .processingMethod(UPDATED_PROCESSING_METHOD)
            .flavorProfile(UPDATED_FLAVOR_PROFILE)
            .coffeeWeight(UPDATED_COFFEE_WEIGHT)
            .waterWeight(UPDATED_WATER_WEIGHT)
            .waterTemp(UPDATED_WATER_TEMP)
            .brewTime(UPDATED_BREW_TIME)
            .brewDate(UPDATED_BREW_DATE)
            .equipment(UPDATED_EQUIPMENT)
            .notes(UPDATED_NOTES)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restBrewCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrewCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBrewCard))
            )
            .andExpect(status().isOk());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
        BrewCard testBrewCard = brewCardList.get(brewCardList.size() - 1);
        assertThat(testBrewCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrewCard.getBrewMethod()).isEqualTo(UPDATED_BREW_METHOD);
        assertThat(testBrewCard.getCoffeeType()).isEqualTo(UPDATED_COFFEE_TYPE);
        assertThat(testBrewCard.getCoffeeRegion()).isEqualTo(UPDATED_COFFEE_REGION);
        assertThat(testBrewCard.getCoffeeSubregion()).isEqualTo(UPDATED_COFFEE_SUBREGION);
        assertThat(testBrewCard.getRoastLevel()).isEqualTo(UPDATED_ROAST_LEVEL);
        assertThat(testBrewCard.getProcessingMethod()).isEqualTo(UPDATED_PROCESSING_METHOD);
        assertThat(testBrewCard.getFlavorProfile()).isEqualTo(UPDATED_FLAVOR_PROFILE);
        assertThat(testBrewCard.getCoffeeWeight()).isEqualTo(UPDATED_COFFEE_WEIGHT);
        assertThat(testBrewCard.getWaterWeight()).isEqualTo(UPDATED_WATER_WEIGHT);
        assertThat(testBrewCard.getWaterTemp()).isEqualTo(UPDATED_WATER_TEMP);
        assertThat(testBrewCard.getBrewTime()).isEqualTo(UPDATED_BREW_TIME);
        assertThat(testBrewCard.getBrewDate()).isEqualTo(UPDATED_BREW_DATE);
        assertThat(testBrewCard.getEquipment()).isEqualTo(UPDATED_EQUIPMENT);
        assertThat(testBrewCard.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testBrewCard.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testBrewCard.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, brewCardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrewCard() throws Exception {
        int databaseSizeBeforeUpdate = brewCardRepository.findAll().size();
        brewCard.setId(count.incrementAndGet());

        // Create the BrewCard
        BrewCardDTO brewCardDTO = brewCardMapper.toDto(brewCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrewCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(brewCardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BrewCard in the database
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrewCard() throws Exception {
        // Initialize the database
        brewCardRepository.saveAndFlush(brewCard);

        int databaseSizeBeforeDelete = brewCardRepository.findAll().size();

        // Delete the brewCard
        restBrewCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, brewCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BrewCard> brewCardList = brewCardRepository.findAll();
        assertThat(brewCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
