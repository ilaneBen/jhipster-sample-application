package fr.it_akademy.test.service.impl;

import fr.it_akademy.test.domain.Prime;
import fr.it_akademy.test.repository.PrimeRepository;
import fr.it_akademy.test.service.PrimeService;
import fr.it_akademy.test.service.dto.PrimeDTO;
import fr.it_akademy.test.service.mapper.PrimeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.test.domain.Prime}.
 */
@Service
@Transactional
public class PrimeServiceImpl implements PrimeService {

    private final Logger log = LoggerFactory.getLogger(PrimeServiceImpl.class);

    private final PrimeRepository primeRepository;

    private final PrimeMapper primeMapper;

    public PrimeServiceImpl(PrimeRepository primeRepository, PrimeMapper primeMapper) {
        this.primeRepository = primeRepository;
        this.primeMapper = primeMapper;
    }

    @Override
    public PrimeDTO save(PrimeDTO primeDTO) {
        log.debug("Request to save Prime : {}", primeDTO);
        Prime prime = primeMapper.toEntity(primeDTO);
        prime = primeRepository.save(prime);
        return primeMapper.toDto(prime);
    }

    @Override
    public PrimeDTO update(PrimeDTO primeDTO) {
        log.debug("Request to update Prime : {}", primeDTO);
        Prime prime = primeMapper.toEntity(primeDTO);
        prime = primeRepository.save(prime);
        return primeMapper.toDto(prime);
    }

    @Override
    public Optional<PrimeDTO> partialUpdate(PrimeDTO primeDTO) {
        log.debug("Request to partially update Prime : {}", primeDTO);

        return primeRepository
            .findById(primeDTO.getId())
            .map(existingPrime -> {
                primeMapper.partialUpdate(existingPrime, primeDTO);

                return existingPrime;
            })
            .map(primeRepository::save)
            .map(primeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrimeDTO> findAll() {
        log.debug("Request to get all Primes");
        return primeRepository.findAll().stream().map(primeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrimeDTO> findOne(Long id) {
        log.debug("Request to get Prime : {}", id);
        return primeRepository.findById(id).map(primeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prime : {}", id);
        primeRepository.deleteById(id);
    }
}
