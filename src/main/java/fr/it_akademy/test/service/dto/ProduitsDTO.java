package fr.it_akademy.test.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.test.domain.Produits} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitsDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Double prix;

    private String photo;

    private String description;

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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitsDTO)) {
            return false;
        }

        ProduitsDTO produitsDTO = (ProduitsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitsDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prix=" + getPrix() +
            ", photo='" + getPhoto() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
