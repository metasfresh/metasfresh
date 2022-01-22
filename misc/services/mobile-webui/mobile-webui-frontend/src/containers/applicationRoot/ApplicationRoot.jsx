import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { ConnectedRouter, push } from 'connected-react-router';

import { useAuth } from '../../hooks/useAuth';
import useConstructor from '../../hooks/useConstructor';
import { routesArray } from '../../routes';

import './ApplicationRoot.css';
import UpdateCheck from '../../components/UpdateCheck';
import { REGISTER_SERVICE_WORKER, UPDATE_CHECK_INTERVAL } from '../../constants';
import { getApplications } from '../../api/applications';
import { populateApplications } from '../../actions/ApplicationsActions';
import { history } from '../../store/store';
import { Route, Switch } from 'react-router';
import LoginScreen from '../LoginScreen';
import PrivateRoute from '../../routes/PrivateRoute';
import ViewHeader from '../ViewHeader';
import ScreenToaster from '../../components/ScreenToaster';
import ApplicationsListScreen from '../applicationsListScreen/ApplicationsListScreen';
import PropTypes from 'prop-types';
import { getApplicationInfoById } from '../../reducers/applications';
import { useHistory, useRouteMatch } from 'react-router-dom';

const ApplicationRoot = () => {
  const auth = useAuth();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.appHandler.token);

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

  useEffect(() => {
    if (token) {
      getApplications().then(({ applications }) => {
        dispatch(populateApplications({ applications }));
      });
    }
  }, [token]);

  return (
    <>
      <ConnectedRouter history={history} basename="./">
        <Switch>
          <Route exact path="/login">
            <LoginScreen />
          </Route>
          <PrivateRoute path="/">
            <>
              <Route key="/" exact path="/">
                <ApplicationsListScreen />
              </Route>
              {routesArray.map(({ path, Component, applicationId }) => (
                <Route key={path} exact path={path}>
                  <ApplicationLayout applicationId={applicationId} Component={Component} />
                </Route>
              ))}
            </>
          </PrivateRoute>
        </Switch>
      </ConnectedRouter>
      {REGISTER_SERVICE_WORKER && <UpdateCheck updateInterval={UPDATE_CHECK_INTERVAL} />}
    </>
  );
};

const ApplicationLayout = ({ applicationId, Component }) => {
  const applicationInfo = getApplicationInfo(applicationId);
  const history = useHistory();

  return (
    <div className="app-container">
      <div className="app-header">
        <div className="columns is-mobile is-size-3">
          <div className="column is-2 app-icon">
            <span className="icon">
              <i className={applicationInfo.iconClassNames} />
            </span>
          </div>
          <div className="column">
            <span>{applicationInfo.caption}</span>
          </div>
        </div>
      </div>
      <div className="app-content">
        <ViewHeader />
        <Component />
        <ScreenToaster />
      </div>
      <div className="app-footer">
        <div className="app-footer">
          <div className="columns is-mobile">
            <div className="column is-half">
              <button className="button is-fullwidth" onClick={() => history.goBack()}>
                <span className="icon">
                  <i className="fas fa-chevron-left" />
                </span>
                <span>Back</span>
              </button>
            </div>
            <div className="column is-half">
              <button className="button is-fullwidth" onClick={() => history.push('/')}>
                <span className="icon">
                  <i className="fas fa-home" />
                </span>
                <span>Home</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

ApplicationLayout.propTypes = {
  applicationId: PropTypes.string,
  Component: PropTypes.func.isRequired,
};

const getApplicationInfo = (knownApplicationId) => {
  let applicationId;
  if (knownApplicationId) {
    applicationId = knownApplicationId;
  } else {
    const routerMatch = useRouteMatch();
    applicationId = routerMatch.params.applicationId;
  }

  return useSelector((state) => getApplicationInfoById({ state, applicationId }));
};

export default ApplicationRoot;
