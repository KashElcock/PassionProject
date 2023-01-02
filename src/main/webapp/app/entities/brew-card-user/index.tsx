import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BrewCardUser from './brew-card-user';
// import BrewCardUserDetail from './brew-card-user-detail';
// import BrewCardUserUpdate from './brew-card-user-update';
// import BrewCardUserDeleteDialog from './brew-card-user-delete-dialog';

const BrewCardUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BrewCardUser />} />
    {/*     <Route path="new" element={<BrewCardUserUpdate />} /> */}
    <Route path=":id">
      {/*       <Route index element={<BrewCardUserDetail />} /> */}
      {/*       <Route path="edit" element={<BrewCardUserUpdate />} /> */}
      {/*       <Route path="delete" element={<BrewCardUserDeleteDialog />} /> */}
    </Route>
  </ErrorBoundaryRoutes>
);

export default BrewCardUserRoutes;
