import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import LoginView from '../components/LoginView';
import Dashboard from '../components/Dashboard';
import LineScreen from '../components/containers/LineScreen';
import PickScreen from '../components/containers/PickScreen';
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
            <Route exact path="/workflow/:workflowId/activityId/:activityId/lineId/:lineId" component={LineScreen} />
            <Route
              exact
              path="/workflow/:workflowId/activityId/:activityId/lineId/:lineId/step/stepId"
              component={PickScreen}
            />
          </PrivateRoute>
        </Switch>
      </Main>
    </ConnectedRouter>
  );
};

export default Routes;
