import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Produits from './produits';
import Vendeur from './vendeur';
import Prime from './prime';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="produits/*" element={<Produits />} />
        <Route path="vendeur/*" element={<Vendeur />} />
        <Route path="prime/*" element={<Prime />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
