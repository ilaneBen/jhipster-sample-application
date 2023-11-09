package fr.it_akademy.test.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.it_akademy.test.domain.Vendeur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendeurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Double nbrVendu;

    private Boolean objectifAtteint;

    private Set<PrimeDTO> primes = new HashSet<>();

    private ProduitsDTO produits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getNbrVendu() {
        return nbrVendu;
    }

    public void setNbrVendu(Double nbrVendu) {
        this.nbrVendu = nbrVendu;
    }

    public Boolean getObjectifAtteint() {
        return objectifAtteint;
    }

    public void setObjectifAtteint(Boolean objectifAtteint) {
        this.objectifAtteint = objectifAtteint;
    }

    public Set<PrimeDTO> getPrimes() {
        return primes;
    }

    public void setPrimes(Set<PrimeDTO> primes) {
        this.primes = primes;
    }

    public ProduitsDTO getProduits() {
        return produits;
    }

    public void setProduits(ProduitsDTO produits) {
        this.produits = produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendeurDTO)) {
            return false;
        }

        VendeurDTO vendeurDTO = (VendeurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vendeurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendeurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nbrVendu=" + getNbrVendu() +
            ", objectifAtteint='" + getObjectifAtteint() + "'" +
            ", primes=" + getPrimes() +
            ", produits=" + getProduits() +
            "}";
    }
}
