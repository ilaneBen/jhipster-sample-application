package fr.it_akademy.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Magasin.
 */
@Entity
@Table(name = "magasin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Magasin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "magasin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendeurs", "magasin" }, allowSetters = true)
    private Set<Produits> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Magasin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Magasin nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Produits> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produits> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setMagasin(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setMagasin(this));
        }
        this.produits = produits;
    }

    public Magasin produits(Set<Produits> produits) {
        this.setProduits(produits);
        return this;
    }

    public Magasin addProduits(Produits produits) {
        this.produits.add(produits);
        produits.setMagasin(this);
        return this;
    }

    public Magasin removeProduits(Produits produits) {
        this.produits.remove(produits);
        produits.setMagasin(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Magasin)) {
            return false;
        }
        return getId() != null && getId().equals(((Magasin) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Magasin{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
