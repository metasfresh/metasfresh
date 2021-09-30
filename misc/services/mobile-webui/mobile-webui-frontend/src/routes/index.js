import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import LoginView from '../components/LoginView';
import Dashboard from '../components/Dashboard';
import WorkflowProcess from '../containers/WFProcess';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';

const Routes = () => {
  return (
    <ConnectedRouter history={history}>
      <Main>
        <Switch>
          <Route exact path="/login" component={LoginView} />
          <PrivateRoute path="/">
            <Route exact path="/" component={Dashboard} />
            <Route exact path="/workflow/:workflowId" component={WorkflowProcess} />
          </PrivateRoute>
        </Switch>
      </Main>
    </ConnectedRouter>
  );
};

export default Routes;
