import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import LoginView from '../components/LoginView';
import Dashboard from '../components/Dashboard';
import TestPage from '../components/TestPage';
import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';

const Routes = () => {
  return (
    <ConnectedRouter history={history}>
      <>
        <Switch>
          <Route exact path="/login" component={LoginView} />
          <PrivateRoute path="/">
            <Route exact path="/" component={Dashboard} />
            <Route exact path="/test" component={TestPage} />
          </PrivateRoute>
        </Switch>
      </>
    </ConnectedRouter>
  );
};

export default Routes;
