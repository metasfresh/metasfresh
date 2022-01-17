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
import Header from './components/Header';
import LoginView from './components/LoginView';
import PrivateRoute from './routes/PrivateRoute';
import ViewHeader from './containers/ViewHeader';
import ScreenToaster from './components/ScreenToaster';

function App() {
  const auth = useAuth();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.appHandler.token);

  useConstructor(() => {
    axios.interceptors.response.use(undefined, function (error) {
      /*
       * Authorization error
       */
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
            <div>
              {routesArray.map(({ path, Component, applicationId }) => (
                <Route key={path} exact path={path}>
                  <Header applicationId={applicationId} />
                  <ViewHeader />
                  <Component />
                  <ScreenToaster />
                </Route>
              ))}
            </div>
          </PrivateRoute>
        </Switch>
      </ConnectedRouter>
      {REGISTER_SERVICE_WORKER && <UpdateCheck updateInterval={UPDATE_CHECK_INTERVAL} />}
    </div>
  );
}

export default App;
