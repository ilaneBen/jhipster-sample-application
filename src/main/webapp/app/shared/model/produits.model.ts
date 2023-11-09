import { IVendeur } from 'app/shared/model/vendeur.model';

export interface IProduits {
  id?: number;
  nom?: string;
  prix?: number;
  photo?: string | null;
  description?: string | null;
  vendeurs?: IVendeur[] | null;
}

export const defaultValue: Readonly<IProduits> = {};
