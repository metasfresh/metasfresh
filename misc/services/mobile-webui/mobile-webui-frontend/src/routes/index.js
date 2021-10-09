import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';
import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import LineScreen from '../containers/activities/picking/LineScreen';
import PickScreen from '../containers/activities/picking/PickScreen';
import ScanScreen from '../containers/activities/scan/ScanScreen';
import PickingScanHUScreen from '../containers/activities/picking/PickingScanHUScreen';
import WFProcessScreen from '../containers/WFProcessScreen';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';

const routesArray = [
  { path: '/', Component: WFLaunchersScreen },
  { path: '/workflow/:workflowId', Component: WFProcessScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId', Component: LineScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId', Component: PickScreen },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/scanner',
    Component: PickingScanHUScreen,
  },
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
