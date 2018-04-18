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

export const getRoutes = (store, auth, plugins) => {
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

  const getPluginsRoutes = plugins => {
    if (plugins.length) {
      return plugins.map((plugin, i) => {
        if (plugin.userDropdownLink) {
          return (
            <Route
              key={i}
              path={`/window/${plugin.userDropdownLink.url}`}
              component={plugin.component}
            />
          );
        }
      });
    }
  };

  const childRoutes = [
    {
      path: '/window/:windowType',
      getComponent: nextState => (
        <DocList
          query={nextState.location.query}
          windowType={nextState.params.windowType}
        />
      )
    },
    {
      path: '/window/:windowType/:docId',
      component: MasterWindow,
      onEnter: nextState =>
        store.dispatch(
          createWindow(nextState.params.windowType, nextState.params.docId)
        )
    },
    {
      path: '/sitemap',
      component: NavigationTree,
    },
    {
      path: '/board/:boardId',
      getComponent: nextState => (
        <Board
          query={nextState.location.query}
          boardId={nextState.params.boardId}
        />
      )
    },
    {
      path: '/inbox',
      component: InboxAll,
    },
    {
      path: 'logout',
      component: logout,
    },
    getPluginsRoutes(plugins)
  ];

  return (
    <Route path="/">
      <Route onEnter={authRequired}
        childRoutes={childRoutes}
      >
        <IndexRoute component={Dashboard} />
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

  return routes;
};
