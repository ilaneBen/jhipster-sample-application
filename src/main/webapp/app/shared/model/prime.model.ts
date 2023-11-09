import { IVendeur } from 'app/shared/model/vendeur.model';

export interface IPrime {
  id?: number;
  nomVendeur?: string | null;
  montant?: number | null;
  vendeurs?: IVendeur[] | null;
}

export const defaultValue: Readonly<IPrime> = {};
