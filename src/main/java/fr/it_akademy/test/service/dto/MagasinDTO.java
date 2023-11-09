package fr.it_akademy.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.test.domain.Magasin} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MagasinDTO implements Serializable {

    private Long id;

    private String nom;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MagasinDTO)) {
            return false;
        }

        MagasinDTO magasinDTO = (MagasinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, magasinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MagasinDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
