package fr.it_akademy.test.service.mapper;

import fr.it_akademy.test.domain.Prime;
import fr.it_akademy.test.service.dto.PrimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prime} and its DTO {@link PrimeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrimeMapper extends EntityMapper<PrimeDTO, Prime> {}
