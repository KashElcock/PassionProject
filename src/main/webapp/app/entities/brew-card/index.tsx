import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BrewCard from './brew-card';
import BrewCardDetail from './brew-card-detail';
import BrewCardUpdate from './brew-card-update';
import BrewCardDeleteDialog from './brew-card-delete-dialog';

const BrewCardRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BrewCard />} />
    <Route path="new" element={<BrewCardUpdate />} />
    <Route path=":id">
      <Route index element={<BrewCardDetail />} />
      <Route path="edit" element={<BrewCardUpdate />} />
      <Route path="delete" element={<BrewCardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BrewCardRoutes;
