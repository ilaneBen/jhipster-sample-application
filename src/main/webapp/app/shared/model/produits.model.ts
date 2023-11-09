import { IVendeur } from 'app/shared/model/vendeur.model';
import { IMagasin } from 'app/shared/model/magasin.model';

export interface IProduits {
  id?: number;
  nom?: string;
  prix?: number;
  photo?: string | null;
  description?: string | null;
  vendeurs?: IVendeur[] | null;
  magasin?: IMagasin | null;
}

export const defaultValue: Readonly<IProduits> = {};
