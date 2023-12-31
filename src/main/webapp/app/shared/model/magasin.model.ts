import { IProduits } from 'app/shared/model/produits.model';

export interface IMagasin {
  id?: number;
  nom?: string;
  produits?: IProduits[] | null;
}

export const defaultValue: Readonly<IMagasin> = {};
