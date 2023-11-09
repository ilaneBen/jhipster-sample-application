package fr.it_akademy.test.service.mapper;

import fr.it_akademy.test.domain.Magasin;
import fr.it_akademy.test.service.dto.MagasinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Magasin} and its DTO {@link MagasinDTO}.
 */
@Mapper(componentModel = "spring")
public interface MagasinMapper extends EntityMapper<MagasinDTO, Magasin> {}
