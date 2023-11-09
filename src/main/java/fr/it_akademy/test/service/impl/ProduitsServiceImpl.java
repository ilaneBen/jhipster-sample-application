package fr.it_akademy.test.service.impl;

import fr.it_akademy.test.domain.Produits;
import fr.it_akademy.test.repository.ProduitsRepository;
import fr.it_akademy.test.service.ProduitsService;
import fr.it_akademy.test.service.dto.ProduitsDTO;
import fr.it_akademy.test.service.mapper.ProduitsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.test.domain.Produits}.
 */
@Service
@Transactional
public class ProduitsServiceImpl implements ProduitsService {

    private final Logger log = LoggerFactory.getLogger(ProduitsServiceImpl.class);

    private final ProduitsRepository produitsRepository;

    private final ProduitsMapper produitsMapper;

    public ProduitsServiceImpl(ProduitsRepository produitsRepository, ProduitsMapper produitsMapper) {
        this.produitsRepository = produitsRepository;
        this.produitsMapper = produitsMapper;
    }

    @Override
    public ProduitsDTO save(ProduitsDTO produitsDTO) {
        log.debug("Request to save Produits : {}", produitsDTO);
        Produits produits = produitsMapper.toEntity(produitsDTO);
        produits = produitsRepository.save(produits);
        return produitsMapper.toDto(produits);
    }

    @Override
    public ProduitsDTO update(ProduitsDTO produitsDTO) {
        log.debug("Request to update Produits : {}", produitsDTO);
        Produits produits = produitsMapper.toEntity(produitsDTO);
        produits = produitsRepository.save(produits);
        return produitsMapper.toDto(produits);
    }

    @Override
    public Optional<ProduitsDTO> partialUpdate(ProduitsDTO produitsDTO) {
        log.debug("Request to partially update Produits : {}", produitsDTO);

        return produitsRepository
            .findById(produitsDTO.getId())
            .map(existingProduits -> {
                produitsMapper.partialUpdate(existingProduits, produitsDTO);

                return existingProduits;
            })
            .map(produitsRepository::save)
            .map(produitsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduitsDTO> findAll() {
        log.debug("Request to get all Produits");
        return produitsRepository.findAll().stream().map(produitsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProduitsDTO> findOne(Long id) {
        log.debug("Request to get Produits : {}", id);
        return produitsRepository.findById(id).map(produitsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produits : {}", id);
        produitsRepository.deleteById(id);
    }
}
