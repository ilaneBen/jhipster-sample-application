import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prime from './prime';
import PrimeDetail from './prime-detail';
import PrimeUpdate from './prime-update';
import PrimeDeleteDialog from './prime-delete-dialog';

const PrimeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prime />} />
    <Route path="new" element={<PrimeUpdate />} />
    <Route path=":id">
      <Route index element={<PrimeDetail />} />
      <Route path="edit" element={<PrimeUpdate />} />
      <Route path="delete" element={<PrimeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrimeRoutes;
