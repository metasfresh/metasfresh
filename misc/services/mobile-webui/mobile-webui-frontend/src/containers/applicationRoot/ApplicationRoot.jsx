import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { ConnectedRouter, push } from 'connected-react-router';

import { useAuth } from '../../hooks/useAuth';
import useConstructor from '../../hooks/useConstructor';
import { routesArray } from '../../routes';

import './ApplicationRoot.css';
import VersionChecker from '../../components/VersionChecker';
import { REGISTER_SERVICE_WORKER, VERSION_CHECK_INTERVAL_MILLIS } from '../../constants';
import { history } from '../../store/store';
import { Route, Switch } from 'react-router';
import PrivateRoute from '../../routes/PrivateRoute';
import LoginScreen from '../LoginScreen';
import ApplicationsListScreen from '../applicationsListScreen/ApplicationsListScreen';
import { ApplicationLayout } from './ApplicationLayout';
import * as api from '../../api/applications';
import { populateApplications } from '../../actions/ApplicationsActions';
import { toastError } from '../../utils/toast';
import { getIsLoggedInFromState } from '../../reducers/appHandler';
import { putSettingsAction } from '../../reducers/settings';
import { useUIEventsTracing } from '../../utils/ui_trace/useUIEventsTracing';
import ScreenToaster from '../../components/ScreenToaster';

const ApplicationRoot = () => {
  const auth = useAuth();
  const dispatch = useDispatch();
  useUIEventsTracing();

  const handleSettingsResponse = (map) => {
    window.showAllErrorMessages = map?.showAllErrorMessages === 'Y';
    dispatch(putSettingsAction(map));
  };

  // If endpoint call returned 401 - Authentication error
  // then redirect user to login page
  useConstructor(() => {
    axios.interceptors.response.use(undefined, function (error) {
      if (error.response && error.response.status === 401) {
        auth.logout().finally(() => {
          dispatch(push('/login'));
        });
      } else {
        return Promise.reject(error);
      }
    });
  });

  const isLoggedIn = useSelector((state) => getIsLoggedInFromState(state));
  useEffect(() => {
    if (isLoggedIn) {
      api
        .getApplications()
        .then(({ applications }) => {
          dispatch(populateApplications({ applications }));
        })
        .catch((axiosError) => toastError({ axiosError }));
    }
  }, [isLoggedIn]);
  useEffect(() => {
    if (isLoggedIn) {
      api
        .getSettings()
        .then(handleSettingsResponse)
        .catch((axiosError) => console.log('Failed to fetch settings', { axiosError }));
    }
  }, [isLoggedIn]);

  return (
    <>
      <ConnectedRouter history={history} basename="./">
        <Switch>
          <Route exact path="/login">
            <LoginScreen />
          </Route>
          <PrivateRoute path="/">
            <Route key="/" exact path="/">
              <ApplicationsListScreen />
            </Route>
            {routesArray.map(({ path, Component, applicationId }) => (
              <Route key={path} exact path={path}>
                <ApplicationLayout applicationId={applicationId} Component={Component} />
              </Route>
            ))}
          </PrivateRoute>
        </Switch>
        {/* ONE toast container for the whole app: react-toastify removes an unmounted container
            from its registry via setTimeout, and the next container to mount cancels all pending
            removals - so a route swap (unmount + mount in the same tick) orphaned the old entry
            for good, and that entry holds a callback bound to the old screen's fiber, pinning its
            whole DOM. A single container never unmounts, so there is nothing left to cancel.
            Inside the router so useLocationChange still sees route changes.

            AFTER <Switch>, not before: the toast container and .prompt-dialog BOTH carry
            z-index 9999 (ReactToastify.css / assets/prompt-dialog.scss), so paint order is DOM
            order. Mounted ahead of the routes the toast rendered UNDERNEATH the full-screen
            dialog overlay - present in the DOM but unclickable, which is what the previously
            per-screen mounts (rendered after <Component/>) had always avoided. */}
        <ScreenToaster />
      </ConnectedRouter>
      {REGISTER_SERVICE_WORKER && <VersionChecker updateIntervalMillis={VERSION_CHECK_INTERVAL_MILLIS} />}
    </>
  );
};

export default ApplicationRoot;
