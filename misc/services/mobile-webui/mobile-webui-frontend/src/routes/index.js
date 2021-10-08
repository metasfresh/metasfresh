import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';
import Launchers from '../containers/Launchers';
import LineScreen from '../components/containers/LineScreen';
import PickScreen from '../components/containers/PickScreen';
import ScanScreen from '../components/containers/ScanScreen';
import WorkflowProcess from '../containers/WFProcess';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';

const routesArray = [
  { path: '/', Component: Launchers },
  { path: '/workflow/:workflowId', Component: WorkflowProcess },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId', Component: LineScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId', Component: PickScreen },
];

const childRoutes = (
  <div>
    {routesArray.map(({ path, Component }) => (
      <Route key={path} exact path={path}>
        <Header appName="Kommissionierung" hidden />
        <ViewHeader />
        <Component />
      </Route>
    ))}
  </div>
);

const Routes = () => {
  return (
    <ConnectedRouter history={history}>
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
