package fr.it_akademy.test.service.mapper;

import fr.it_akademy.test.domain.Prime;
import fr.it_akademy.test.domain.Produits;
import fr.it_akademy.test.domain.Vendeur;
import fr.it_akademy.test.service.dto.PrimeDTO;
import fr.it_akademy.test.service.dto.ProduitsDTO;
import fr.it_akademy.test.service.dto.VendeurDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendeur} and its DTO {@link VendeurDTO}.
 */
@Mapper(componentModel = "spring")
public interface VendeurMapper extends EntityMapper<VendeurDTO, Vendeur> {
    @Mapping(target = "primes", source = "primes", qualifiedByName = "primeIdSet")
    @Mapping(target = "produits", source = "produits", qualifiedByName = "produitsId")
    VendeurDTO toDto(Vendeur s);

    @Mapping(target = "removePrime", ignore = true)
    Vendeur toEntity(VendeurDTO vendeurDTO);

    @Named("primeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PrimeDTO toDtoPrimeId(Prime prime);

    @Named("primeIdSet")
    default Set<PrimeDTO> toDtoPrimeIdSet(Set<Prime> prime) {
        return prime.stream().map(this::toDtoPrimeId).collect(Collectors.toSet());
    }

    @Named("produitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitsDTO toDtoProduitsId(Produits produits);
}
