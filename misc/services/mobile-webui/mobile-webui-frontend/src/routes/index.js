import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';

import { commonRoutes } from './common';
import { manufacturingRoutes } from './manufacturing';
import { distributionRoutes } from './distribution';
import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';
import ScreenToaster from '../components/ScreenToaster';

const routesArray = [...commonRoutes, ...distributionRoutes, ...manufacturingRoutes];

const childRoutes = (
  <div>
    {routesArray.map(({ path, Component }) => (
      <Route key={path} exact path={path}>
        <Header appName="metasfresh mobile" hidden />
        <ViewHeader />
        <Component />
        <ScreenToaster />
      </Route>
    ))}
  </div>
);

const Routes = () => {
  return (
    <ConnectedRouter history={history} basename="./">
      <Main>
        <Switch>
          <Route exact path="/login" component={LoginView} />
          <PrivateRoute path="/">{childRoutes}</PrivateRoute>
        </Switch>
      </Main>
    </ConnectedRouter>
  );
};

export default Routes;
