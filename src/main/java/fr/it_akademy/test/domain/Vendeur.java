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
 * A Vendeur.
 */
@Entity
@Table(name = "vendeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vendeur implements Serializable {

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
    @Column(name = "nbr_vendu", nullable = false)
    private Double nbrVendu;

    @Column(name = "objectif_atteint")
    private Boolean objectifAtteint;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_vendeur__prime",
        joinColumns = @JoinColumn(name = "vendeur_id"),
        inverseJoinColumns = @JoinColumn(name = "prime_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendeurs" }, allowSetters = true)
    private Set<Prime> primes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vendeurs" }, allowSetters = true)
    private Produits produits;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vendeur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Vendeur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getNbrVendu() {
        return this.nbrVendu;
    }

    public Vendeur nbrVendu(Double nbrVendu) {
        this.setNbrVendu(nbrVendu);
        return this;
    }

    public void setNbrVendu(Double nbrVendu) {
        this.nbrVendu = nbrVendu;
    }

    public Boolean getObjectifAtteint() {
        return this.objectifAtteint;
    }

    public Vendeur objectifAtteint(Boolean objectifAtteint) {
        this.setObjectifAtteint(objectifAtteint);
        return this;
    }

    public void setObjectifAtteint(Boolean objectifAtteint) {
        this.objectifAtteint = objectifAtteint;
    }

    public Set<Prime> getPrimes() {
        return this.primes;
    }

    public void setPrimes(Set<Prime> primes) {
        this.primes = primes;
    }

    public Vendeur primes(Set<Prime> primes) {
        this.setPrimes(primes);
        return this;
    }

    public Vendeur addPrime(Prime prime) {
        this.primes.add(prime);
        return this;
    }

    public Vendeur removePrime(Prime prime) {
        this.primes.remove(prime);
        return this;
    }

    public Produits getProduits() {
        return this.produits;
    }

    public void setProduits(Produits produits) {
        this.produits = produits;
    }

    public Vendeur produits(Produits produits) {
        this.setProduits(produits);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendeur)) {
            return false;
        }
        return getId() != null && getId().equals(((Vendeur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vendeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nbrVendu=" + getNbrVendu() +
            ", objectifAtteint='" + getObjectifAtteint() + "'" +
            "}";
    }
}
