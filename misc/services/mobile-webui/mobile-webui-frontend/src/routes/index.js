import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';
import ScreenToaster from '../components/ScreenToaster';

import { commonRoutes } from './common';
import { launchersRoutes } from './launchers';
import { workflowRoutes } from './workflow';
import { manufacturingIssueRoutes } from './manufacturing_issue';
import { manufacturingReceiptRoutes } from './manufacturing_receipt';
import { distributionRoutes } from './distribution';
import { pickingRoutes } from './picking';
import { getApplicationRoutes } from '../apps';

const routesArray = [
  ...commonRoutes,
  ...launchersRoutes,
  ...workflowRoutes,
  ...distributionRoutes,
  ...manufacturingIssueRoutes,
  ...manufacturingReceiptRoutes,
  ...pickingRoutes,
  ...getApplicationRoutes(),
];

const Routes = () => {
  return (
    <ConnectedRouter history={history} basename="./">
      <Switch>
        <Route exact path="/login">
          <Header />
          <LoginView />
        </Route>
        <PrivateRoute path="/">
          <div>
            {routesArray.map(({ path, Component, applicationId }) => (
              <Route key={path} exact path={path}>
                <Header applicationId={applicationId} />
                <ViewHeader />
                <Component />
                <ScreenToaster />
              </Route>
            ))}
          </div>
        </PrivateRoute>
      </Switch>
    </ConnectedRouter>
  );
};

export default Routes;
