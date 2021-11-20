import React from 'react';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';

import Main from '../components/Main';
import Header from '../components/Header';
import ViewHeader from '../containers/ViewHeader';
import LoginView from '../components/LoginView';

import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import ScanScreen from '../containers/activities/scan/ScanScreen';
import LineScreen from '../containers/activities/common/LineScreen';
import StepScreen from '../containers/activities/common/StepScreen';
import StepScanScreen from '../containers/activities/common/StepScanScreen';
import ApplicationsScreen from '../containers/applicationScreen/ApplicationsScreen';

import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/ReceiptNewHUScreen';

import PrivateRoute from './PrivateRoute';
import { history } from '../store/store';
import ScreenToaster from '../components/ScreenToaster';

const routesArray = [
  { path: '/', Component: ApplicationsScreen },
  { path: '/workflow/:workflowId', Component: WFProcessScreen },
  { path: '/launchers/:applicationId', Component: WFLaunchersScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId', Component: LineScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId', Component: StepScreen },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/scanner/:appId/:locatorId?',
    Component: StepScanScreen,
  },
  // application specific - mfg
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/target',
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/receive/hu',
    Component: ReceiptNewHUScreen,
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
