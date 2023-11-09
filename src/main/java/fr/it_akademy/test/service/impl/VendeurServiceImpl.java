package fr.it_akademy.test.service.impl;

import fr.it_akademy.test.domain.Vendeur;
import fr.it_akademy.test.repository.VendeurRepository;
import fr.it_akademy.test.service.VendeurService;
import fr.it_akademy.test.service.dto.VendeurDTO;
import fr.it_akademy.test.service.mapper.VendeurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.test.domain.Vendeur}.
 */
@Service
@Transactional
public class VendeurServiceImpl implements VendeurService {

    private final Logger log = LoggerFactory.getLogger(VendeurServiceImpl.class);

    private final VendeurRepository vendeurRepository;

    private final VendeurMapper vendeurMapper;

    public VendeurServiceImpl(VendeurRepository vendeurRepository, VendeurMapper vendeurMapper) {
        this.vendeurRepository = vendeurRepository;
        this.vendeurMapper = vendeurMapper;
    }

    @Override
    public VendeurDTO save(VendeurDTO vendeurDTO) {
        log.debug("Request to save Vendeur : {}", vendeurDTO);
        Vendeur vendeur = vendeurMapper.toEntity(vendeurDTO);
        vendeur = vendeurRepository.save(vendeur);
        return vendeurMapper.toDto(vendeur);
    }

    @Override
    public VendeurDTO update(VendeurDTO vendeurDTO) {
        log.debug("Request to update Vendeur : {}", vendeurDTO);
        Vendeur vendeur = vendeurMapper.toEntity(vendeurDTO);
        vendeur = vendeurRepository.save(vendeur);
        return vendeurMapper.toDto(vendeur);
    }

    @Override
    public Optional<VendeurDTO> partialUpdate(VendeurDTO vendeurDTO) {
        log.debug("Request to partially update Vendeur : {}", vendeurDTO);

        return vendeurRepository
            .findById(vendeurDTO.getId())
            .map(existingVendeur -> {
                vendeurMapper.partialUpdate(existingVendeur, vendeurDTO);

                return existingVendeur;
            })
            .map(vendeurRepository::save)
            .map(vendeurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendeurDTO> findAll() {
        log.debug("Request to get all Vendeurs");
        return vendeurRepository.findAll().stream().map(vendeurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<VendeurDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vendeurRepository.findAllWithEagerRelationships(pageable).map(vendeurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendeurDTO> findOne(Long id) {
        log.debug("Request to get Vendeur : {}", id);
        return vendeurRepository.findOneWithEagerRelationships(id).map(vendeurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendeur : {}", id);
        vendeurRepository.deleteById(id);
    }
}
