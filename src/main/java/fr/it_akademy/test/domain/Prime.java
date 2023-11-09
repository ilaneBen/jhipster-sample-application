package fr.it_akademy.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prime.
 */
@Entity
@Table(name = "prime")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_vendeur")
    private String nomVendeur;

    @Column(name = "montant")
    private Double montant;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "primes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "primes", "produits" }, allowSetters = true)
    private Set<Vendeur> vendeurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prime id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomVendeur() {
        return this.nomVendeur;
    }

    public Prime nomVendeur(String nomVendeur) {
        this.setNomVendeur(nomVendeur);
        return this;
    }

    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Prime montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Set<Vendeur> getVendeurs() {
        return this.vendeurs;
    }

    public void setVendeurs(Set<Vendeur> vendeurs) {
        if (this.vendeurs != null) {
            this.vendeurs.forEach(i -> i.removePrime(this));
        }
        if (vendeurs != null) {
            vendeurs.forEach(i -> i.addPrime(this));
        }
        this.vendeurs = vendeurs;
    }

    public Prime vendeurs(Set<Vendeur> vendeurs) {
        this.setVendeurs(vendeurs);
        return this;
    }

    public Prime addVendeur(Vendeur vendeur) {
        this.vendeurs.add(vendeur);
        vendeur.getPrimes().add(this);
        return this;
    }

    public Prime removeVendeur(Vendeur vendeur) {
        this.vendeurs.remove(vendeur);
        vendeur.getPrimes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prime)) {
            return false;
        }
        return getId() != null && getId().equals(((Prime) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prime{" +
            "id=" + getId() +
            ", nomVendeur='" + getNomVendeur() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
