import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vendeur from './vendeur';
import VendeurDetail from './vendeur-detail';
import VendeurUpdate from './vendeur-update';
import VendeurDeleteDialog from './vendeur-delete-dialog';

const VendeurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vendeur />} />
    <Route path="new" element={<VendeurUpdate />} />
    <Route path=":id">
      <Route index element={<VendeurDetail />} />
      <Route path="edit" element={<VendeurUpdate />} />
      <Route path="delete" element={<VendeurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VendeurRoutes;
