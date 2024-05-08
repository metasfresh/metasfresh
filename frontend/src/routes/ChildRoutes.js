import React from 'react';
import { Route, Switch, Redirect, useHistory } from 'react-router-dom';

import { useAuth } from '../hooks/useAuth';
import { MasterWindowRoute, BoardRoute, DocListRoute } from './KeyRoutes';

import Board from '../containers/Board.js';
import Dashboard from '../containers/Dashboard.js';
import InboxAll from '../containers/InboxAll.js';
import NavigationTree from '../containers/NavigationTree.js';

/**
 * @file Functional component.
 * @module routes/ChildRoutes
 * Wrapper around the routes that need to be authentication-protected
 */
const ChildRoutes = () => {
  const auth = useAuth();
  const history = useHistory();
  const loggedIn = auth.isLoggedIn;

  return (
    <>
      <Switch>
        <Route exact path="/" component={Dashboard} />
        <Route
          path="/window/:windowId/:docId"
          render={(params) => <MasterWindowRoute {...params} />}
        />
        <Route path="/window/:windowId" component={DocListRoute} />
        <Route path="/sitemap" component={NavigationTree} />
        <Route path="/board/:boardId" component={BoardRoute} />
        <Route path="/inbox" component={InboxAll} />
        <Route
          path="/logout"
          render={() => {
            if (loggedIn) {
              auth.logout().finally(() => {
                history.push('/login');
                return null;
              });
            } else {
              return <Redirect to="/login" />;
            }
          }}
        />
        <Route path="*" render={(props) => <Board board="404" {...props} />} />
      </Switch>
    </>
  );
};

export default React.memo(ChildRoutes);
