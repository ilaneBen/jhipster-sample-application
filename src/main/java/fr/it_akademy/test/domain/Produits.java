package fr.it_akademy.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produits.
 */
@Entity
@Table(name = "produits")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produits")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "primes", "produits" }, allowSetters = true)
    private Set<Vendeur> vendeurs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Magasin magasin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Produits nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return this.prix;
    }

    public Produits prix(Double prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Produits photo(String photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return this.description;
    }

    public Produits description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vendeur> getVendeurs() {
        return this.vendeurs;
    }

    public void setVendeurs(Set<Vendeur> vendeurs) {
        if (this.vendeurs != null) {
            this.vendeurs.forEach(i -> i.setProduits(null));
        }
        if (vendeurs != null) {
            vendeurs.forEach(i -> i.setProduits(this));
        }
        this.vendeurs = vendeurs;
    }

    public Produits vendeurs(Set<Vendeur> vendeurs) {
        this.setVendeurs(vendeurs);
        return this;
    }

    public Produits addVendeur(Vendeur vendeur) {
        this.vendeurs.add(vendeur);
        vendeur.setProduits(this);
        return this;
    }

    public Produits removeVendeur(Vendeur vendeur) {
        this.vendeurs.remove(vendeur);
        vendeur.setProduits(null);
        return this;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Produits magasin(Magasin magasin) {
        this.setMagasin(magasin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produits)) {
            return false;
        }
        return getId() != null && getId().equals(((Produits) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produits{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prix=" + getPrix() +
            ", photo='" + getPhoto() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
