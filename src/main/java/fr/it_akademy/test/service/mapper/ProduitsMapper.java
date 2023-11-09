package fr.it_akademy.test.service.mapper;

import fr.it_akademy.test.domain.Produits;
import fr.it_akademy.test.service.dto.ProduitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produits} and its DTO {@link ProduitsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitsMapper extends EntityMapper<ProduitsDTO, Produits> {}
