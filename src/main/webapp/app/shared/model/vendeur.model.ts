import { IPrime } from 'app/shared/model/prime.model';
import { IProduits } from 'app/shared/model/produits.model';

export interface IVendeur {
  id?: number;
  nom?: string;
  nbrVendu?: number;
  objectifAtteint?: boolean | null;
  primes?: IPrime[] | null;
  produits?: IProduits | null;
}

export const defaultValue: Readonly<IVendeur> = {
  objectifAtteint: false,
};
