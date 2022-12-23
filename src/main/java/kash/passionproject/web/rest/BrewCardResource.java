package kash.passionproject.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import kash.passionproject.repository.BrewCardRepository;
import kash.passionproject.service.BrewCardService;
import kash.passionproject.service.dto.BrewCardDTO;
import kash.passionproject.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link kash.passionproject.domain.BrewCard}.
 */
@RestController
@RequestMapping("/api")
public class BrewCardResource {

    private final Logger log = LoggerFactory.getLogger(BrewCardResource.class);

    private static final String ENTITY_NAME = "brewCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrewCardService brewCardService;

    private final BrewCardRepository brewCardRepository;

    public BrewCardResource(BrewCardService brewCardService, BrewCardRepository brewCardRepository) {
        this.brewCardService = brewCardService;
        this.brewCardRepository = brewCardRepository;
    }

    /**
     * {@code POST  /brew-cards} : Create a new brewCard.
     *
     * @param brewCardDTO the brewCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brewCardDTO, or with status {@code 400 (Bad Request)} if the brewCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/brew-cards")
    public ResponseEntity<BrewCardDTO> createBrewCard(@Valid @RequestBody BrewCardDTO brewCardDTO) throws URISyntaxException {
        log.debug("REST request to save BrewCard : {}", brewCardDTO);
        if (brewCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new brewCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrewCardDTO result = brewCardService.save(brewCardDTO);
        return ResponseEntity
            .created(new URI("/api/brew-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /brew-cards/:id} : Updates an existing brewCard.
     *
     * @param id the id of the brewCardDTO to save.
     * @param brewCardDTO the brewCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brewCardDTO,
     * or with status {@code 400 (Bad Request)} if the brewCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the brewCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/brew-cards/{id}")
    public ResponseEntity<BrewCardDTO> updateBrewCard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BrewCardDTO brewCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BrewCard : {}, {}", id, brewCardDTO);
        if (brewCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brewCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brewCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BrewCardDTO result = brewCardService.update(brewCardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brewCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /brew-cards/:id} : Partial updates given fields of an existing brewCard, field will ignore if it is null
     *
     * @param id the id of the brewCardDTO to save.
     * @param brewCardDTO the brewCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brewCardDTO,
     * or with status {@code 400 (Bad Request)} if the brewCardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the brewCardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the brewCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/brew-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BrewCardDTO> partialUpdateBrewCard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BrewCardDTO brewCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BrewCard partially : {}, {}", id, brewCardDTO);
        if (brewCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brewCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brewCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BrewCardDTO> result = brewCardService.partialUpdate(brewCardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brewCardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /brew-cards} : get all the brewCards.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brewCards in body.
     */
    @GetMapping("/brew-cards")
    public List<BrewCardDTO> getAllBrewCards(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all BrewCards");
        return brewCardService.findAll();
    }

    /**
     * {@code GET  /brew-cards/:id} : get the "id" brewCard.
     *
     * @param id the id of the brewCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brewCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/brew-cards/{id}")
    public ResponseEntity<BrewCardDTO> getBrewCard(@PathVariable Long id) {
        log.debug("REST request to get BrewCard : {}", id);
        Optional<BrewCardDTO> brewCardDTO = brewCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(brewCardDTO);
    }

    /**
     * {@code DELETE  /brew-cards/:id} : delete the "id" brewCard.
     *
     * @param id the id of the brewCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/brew-cards/{id}")
    public ResponseEntity<Void> deleteBrewCard(@PathVariable Long id) {
        log.debug("REST request to delete BrewCard : {}", id);
        brewCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
