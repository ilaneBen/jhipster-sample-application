package fr.it_akademy.test.repository;

import fr.it_akademy.test.domain.Vendeur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface VendeurRepositoryWithBagRelationships {
    Optional<Vendeur> fetchBagRelationships(Optional<Vendeur> vendeur);

    List<Vendeur> fetchBagRelationships(List<Vendeur> vendeurs);

    Page<Vendeur> fetchBagRelationships(Page<Vendeur> vendeurs);
}
