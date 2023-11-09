package fr.it_akademy.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.test.domain.Prime} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrimeDTO implements Serializable {

    private Long id;

    private String nomVendeur;

    private Double montant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomVendeur() {
        return nomVendeur;
    }

    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrimeDTO)) {
            return false;
        }

        PrimeDTO primeDTO = (PrimeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, primeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrimeDTO{" +
            "id=" + getId() +
            ", nomVendeur='" + getNomVendeur() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
