package fr.it_akademy.test.service.mapper;

import fr.it_akademy.test.domain.Magasin;
import fr.it_akademy.test.domain.Produits;
import fr.it_akademy.test.service.dto.MagasinDTO;
import fr.it_akademy.test.service.dto.ProduitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produits} and its DTO {@link ProduitsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitsMapper extends EntityMapper<ProduitsDTO, Produits> {
    @Mapping(target = "magasin", source = "magasin", qualifiedByName = "magasinId")
    ProduitsDTO toDto(Produits s);

    @Named("magasinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MagasinDTO toDtoMagasinId(Magasin magasin);
}
