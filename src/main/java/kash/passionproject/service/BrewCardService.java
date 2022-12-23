package kash.passionproject.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kash.passionproject.domain.BrewCard;
import kash.passionproject.repository.BrewCardRepository;
import kash.passionproject.service.dto.BrewCardDTO;
import kash.passionproject.service.mapper.BrewCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BrewCard}.
 */
@Service
@Transactional
public class BrewCardService {

    private final Logger log = LoggerFactory.getLogger(BrewCardService.class);

    private final BrewCardRepository brewCardRepository;

    private final BrewCardMapper brewCardMapper;

    public BrewCardService(BrewCardRepository brewCardRepository, BrewCardMapper brewCardMapper) {
        this.brewCardRepository = brewCardRepository;
        this.brewCardMapper = brewCardMapper;
    }

    /**
     * Save a brewCard.
     *
     * @param brewCardDTO the entity to save.
     * @return the persisted entity.
     */
    public BrewCardDTO save(BrewCardDTO brewCardDTO) {
        log.debug("Request to save BrewCard : {}", brewCardDTO);
        BrewCard brewCard = brewCardMapper.toEntity(brewCardDTO);
        brewCard = brewCardRepository.save(brewCard);
        return brewCardMapper.toDto(brewCard);
    }

    /**
     * Update a brewCard.
     *
     * @param brewCardDTO the entity to save.
     * @return the persisted entity.
     */
    public BrewCardDTO update(BrewCardDTO brewCardDTO) {
        log.debug("Request to update BrewCard : {}", brewCardDTO);
        BrewCard brewCard = brewCardMapper.toEntity(brewCardDTO);
        brewCard = brewCardRepository.save(brewCard);
        return brewCardMapper.toDto(brewCard);
    }

    /**
     * Partially update a brewCard.
     *
     * @param brewCardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BrewCardDTO> partialUpdate(BrewCardDTO brewCardDTO) {
        log.debug("Request to partially update BrewCard : {}", brewCardDTO);

        return brewCardRepository
            .findById(brewCardDTO.getId())
            .map(existingBrewCard -> {
                brewCardMapper.partialUpdate(existingBrewCard, brewCardDTO);

                return existingBrewCard;
            })
            .map(brewCardRepository::save)
            .map(brewCardMapper::toDto);
    }

    /**
     * Get all the brewCards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BrewCardDTO> findAll() {
        log.debug("Request to get all BrewCards");
        return brewCardRepository.findAll().stream().map(brewCardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the brewCards with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BrewCardDTO> findAllWithEagerRelationships(Pageable pageable) {
        return brewCardRepository.findAllWithEagerRelationships(pageable).map(brewCardMapper::toDto);
    }

    /**
     * Get one brewCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BrewCardDTO> findOne(Long id) {
        log.debug("Request to get BrewCard : {}", id);
        return brewCardRepository.findOneWithEagerRelationships(id).map(brewCardMapper::toDto);
    }

    /**
     * Delete the brewCard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BrewCard : {}", id);
        brewCardRepository.deleteById(id);
    }
}
