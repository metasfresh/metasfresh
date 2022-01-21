import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { ConnectedRouter, push } from 'connected-react-router';

import { useAuth } from './hooks/useAuth';
import useConstructor from './hooks/useConstructor';
import { routesArray } from './routes';

import './App.css';
import UpdateCheck from './components/UpdateCheck';
import { REGISTER_SERVICE_WORKER, UPDATE_CHECK_INTERVAL } from './constants/index';
import { getApplications } from './api/applications';
import { populateApplications } from './actions/ApplicationsActions';
import { history } from './store/store';
import { Route, Switch } from 'react-router';
import Header from './components/screenHeader/Header';
import LoginView from './components/LoginView';
import PrivateRoute from './routes/PrivateRoute';
import ViewHeader from './containers/ViewHeader';
import ScreenToaster from './components/ScreenToaster';

function App() {
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
    <div className="application">
      <ConnectedRouter history={history} basename="./">
        <Switch>
          <Route exact path="/login">
            <Header />
            <LoginView />
          </Route>
          <PrivateRoute path="/">
            <>
              {routesArray.map(({ path, Component, applicationId }) => (
                <Route key={path} exact path={path}>
                  <Header applicationId={applicationId} />
                  <ViewHeader />
                  <Component />
                  <ScreenToaster />
                </Route>
              ))}
            </>
          </PrivateRoute>
        </Switch>
      </ConnectedRouter>
      {REGISTER_SERVICE_WORKER && <UpdateCheck updateInterval={UPDATE_CHECK_INTERVAL} />}
    </div>
  );
}

export default App;
