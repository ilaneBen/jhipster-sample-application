package fr.it_akademy.test.repository;

import fr.it_akademy.test.domain.Vendeur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VendeurRepositoryWithBagRelationshipsImpl implements VendeurRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Vendeur> fetchBagRelationships(Optional<Vendeur> vendeur) {
        return vendeur.map(this::fetchPrimes);
    }

    @Override
    public Page<Vendeur> fetchBagRelationships(Page<Vendeur> vendeurs) {
        return new PageImpl<>(fetchBagRelationships(vendeurs.getContent()), vendeurs.getPageable(), vendeurs.getTotalElements());
    }

    @Override
    public List<Vendeur> fetchBagRelationships(List<Vendeur> vendeurs) {
        return Optional.of(vendeurs).map(this::fetchPrimes).orElse(Collections.emptyList());
    }

    Vendeur fetchPrimes(Vendeur result) {
        return entityManager
            .createQuery("select vendeur from Vendeur vendeur left join fetch vendeur.primes where vendeur.id = :id", Vendeur.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Vendeur> fetchPrimes(List<Vendeur> vendeurs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, vendeurs.size()).forEach(index -> order.put(vendeurs.get(index).getId(), index));
        List<Vendeur> result = entityManager
            .createQuery("select vendeur from Vendeur vendeur left join fetch vendeur.primes where vendeur in :vendeurs", Vendeur.class)
            .setParameter("vendeurs", vendeurs)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
