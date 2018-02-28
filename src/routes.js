import React from 'react';
import { IndexRoute, NoMatch, Route } from 'react-router';
import { push } from 'react-router-redux';

import {
  clearNotifications,
  enableTutorial,
  localLoginRequest,
  loginSuccess,
  logoutRequest,
  logoutSuccess,
} from './actions/AppActions';
import { createWindow } from './actions/WindowActions';
import Board from './containers/Board.js';
import Dashboard from './containers/Dashboard.js';
import DocList from './containers/DocList.js';
import InboxAll from './containers/InboxAll.js';
import Login from './containers/Login.js';
import MasterWindow from './containers/MasterWindow.js';
import NavigationTree from './containers/NavigationTree.js';

let hasTutorial = false;

export const getRoutes = (store, auth) => {
  const authRequired = (nextState, replace, callback) => {
    hasTutorial =
      nextState &&
      nextState.location &&
      nextState.location.query &&
      typeof nextState.location.query.tutorial !== 'undefined';

    if (!localStorage.isLogged) {
      localLoginRequest().then(resp => {
        if (resp.data) {
          store.dispatch(loginSuccess(auth));
          callback(null, nextState.location.pathname);
        } else {
          //redirect tells that there should be
          //step back in history after login
          store.dispatch(push('/login?redirect=true'));
        }
      });
    } else {
      if (hasTutorial) {
        store.dispatch(enableTutorial());
      }

      store.dispatch(clearNotifications());

      store.dispatch(loginSuccess(auth));

      callback();
    }
  };

  const logout = () => {
    logoutRequest()
      .then(() => logoutSuccess(auth))
      .then(() => store.dispatch(push('/login')));
  };

  return (
    <Route path="/">
      <Route onEnter={authRequired}>
        <IndexRoute component={Dashboard} />
        <Route
          path="/window/:windowType"
          component={nextState => (
            <DocList
              query={nextState.location.query}
              windowType={nextState.params.windowType}
            />
          )}
        />
        <Route
          path="/window/:windowType/:docId"
          component={MasterWindow}
          onEnter={nextState =>
            store.dispatch(
              createWindow(nextState.params.windowType, nextState.params.docId)
            )
          }
        />
        <Route path="/sitemap" component={NavigationTree} />
        <Route
          path="/board/:boardId"
          component={nextState => (
            <Board
              query={nextState.location.query}
              boardId={nextState.params.boardId}
            />
          )}
        />
        <Route path="/inbox" component={InboxAll} />
        <Route path="/logout" onEnter={logout} />
      </Route>
      <Route
        path="/login"
        component={nextState => (
          <Login
            redirect={nextState.location.query.redirect}
            logged={localStorage.isLogged}
            {...{ auth }}
          />
        )}
      />
      <Route path="*" component={NoMatch} />
    </Route>
  );
};
