import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';

import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFProcessScreen from '../containers/WFProcessScreen';
import ScanScreen from '../containers/activities/scan/ScanScreen';
import PickLineScreen from '../containers/activities/picking/PickLineScreen';
import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanHUScreen from '../containers/activities/picking/PickStepScanHUScreen';
import ApplicationsScreen from '../containers/applicationScreen/ApplicationsScreen';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';
import ScreenToaster from '../components/ScreenToaster';

const routesArray = [
  { path: '/', Component: ApplicationsScreen },
  { path: '/workflow/:workflowId', Component: WFProcessScreen },
  { path: '/launchers/:applicationId', Component: WFLaunchersScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId', Component: PickLineScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId', Component: PickStepScreen },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/scanner',
    Component: PickStepScanHUScreen,
  },
];

const childRoutes = (
  <div>
    {routesArray.map(({ path, Component }) => (
      <Route key={path} exact path={path}>
        <Header appName="Kommissionierung" hidden />
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
