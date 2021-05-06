import React from 'react';

import { Route, Switch, Redirect } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import { logoutRequest } from '../api';
import { useAuth } from '../hooks/useAuth';
import history from '../services/History';

import { MasterWindowRoute, BoardRoute, DocListRoute } from './KeyRoutes';

import Dashboard from '../containers/Dashboard.js';
import InboxAll from '../containers/InboxAll.js';
import NavigationTree from '../containers/NavigationTree.js';

const ChildRoutes = () => {
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();
  const dispatch = useDispatch();

  return (
    <>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route
          path="/window/:windowId/:docId"
          render={(params) => (
            <MasterWindowRoute {...params} dispatch={dispatch} />
          )}
        />
        <Route path="/window/:windowId" component={DocListRoute} />
        <Route path="/sitemap" component={NavigationTree} />
        <Route path="/board/:boardId" component={BoardRoute} />
        <Route path="/inbox" component={InboxAll} />

        <Route
          path="/logout"
          render={() => {
            console.log('logout path: ', loggedIn);
            if (loggedIn) {
              logoutRequest()
                .then(() => auth.logoutSuccess())
                .then(() => {
                  console.log('store dispatch');
                  history.push('/login');
                  return null;
                });
            } else {
              return <Redirect to="/login" />;
            }
          }}
        />
      </Switch>
    </>
  );
};

export default React.memo(ChildRoutes);
