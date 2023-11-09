import produits from 'app/entities/produits/produits.reducer';
import vendeur from 'app/entities/vendeur/vendeur.reducer';
import prime from 'app/entities/prime/prime.reducer';
import magasin from 'app/entities/magasin/magasin.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  produits,
  vendeur,
  prime,
  magasin,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
