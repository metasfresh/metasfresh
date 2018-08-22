import React from 'react';
import { IndexRoute, NoMatch, Route } from 'react-router';
import { push } from 'react-router-redux';

import { loginSuccess, logoutSuccess } from './actions/AppActions';
import { localLoginRequest, logoutRequest } from './api';
import { clearNotifications, enableTutorial } from './actions/AppActions';
import { createWindow } from './actions/WindowActions';
import { setBreadcrumb } from './actions/MenuActions';
import Board from './containers/Board.js';
import Dashboard from './containers/Dashboard.js';
import DocList from './containers/DocList.js';
import InboxAll from './containers/InboxAll.js';
import Login from './containers/Login.js';
import MasterWindow from './containers/MasterWindow.js';
import NavigationTree from './containers/NavigationTree.js';
import PluginContainer, { pluginWrapper } from './components/PluginContainer';

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

  function setPluginBreadcrumbHandlers(routesArray, currentBreadcrumb) {
    routesArray.forEach(route => {
      const routeBreadcrumb = [
        ...currentBreadcrumb,
        {
          caption: route.breadcrumb.caption,
          type: route.breadcrumb.type,
        },
      ];

      route.onEnter = () => store.dispatch(setBreadcrumb(routeBreadcrumb));

      if (route.childRoutes) {
        setPluginBreadcrumbHandlers(route.childRoutes, routeBreadcrumb);
      }
    });
  }

  const getPluginsRoutes = plugins => {
    if (plugins.length) {
      const routes = plugins.map(plugin => {
        if (plugin.routes && plugin.routes.length) {
          const pluginRoutes = [...plugin.routes];
          const ParentComponent = pluginRoutes[0].component;

          // wrap main plugin component in a HOC that'll render it
          // inside the app using a Container element
          if (ParentComponent.name !== 'WrappedPlugin') {
            const wrapped = pluginWrapper(PluginContainer, ParentComponent);

            pluginRoutes[0].component = wrapped;

            if (pluginRoutes[0].breadcrumb) {
              setPluginBreadcrumbHandlers(pluginRoutes, []);
            }
          }

          return pluginRoutes[0];
        }

        return [];
      });

      return routes;
    }

    return [];
  };

  const pluginRoutes = getPluginsRoutes(plugins);
  const childRoutes = [
    {
      path: '/window/:windowType',
      component: nextState => (
        <DocList
          query={nextState.location.query}
          windowType={nextState.params.windowType}
        />
      ),
    },
    {
      path: '/window/:windowType/:docId',
      component: MasterWindow,
      onEnter: ({ params }) =>
        store.dispatch(createWindow(params.windowType, params.docId)),
    },
    {
      path: '/sitemap',
      component: NavigationTree,
    },
    {
      path: '/board/:boardId',
      component: nextState => (
        <Board
          query={nextState.location.query}
          boardId={nextState.params.boardId}
        />
      ),
    },
    {
      path: '/inbox',
      component: InboxAll,
    },
    {
      path: 'logout',
      onEnter: logout,
    },
    ...pluginRoutes,
  ];

  return (
    <Route path="/">
      <Route onEnter={authRequired} childRoutes={childRoutes}>
        <IndexRoute component={Dashboard} />
      </Route>
      <Route
        path="/login"
        component={({ location }) => (
          <Login
            redirect={location.query.redirect}
            logged={localStorage.isLogged}
            {...{ auth }}
          />
        )}
      />
      <Route
        path="/forgottenPassword"
        component={({ location }) => (
          <Login splat={location.pathname.replace('/', '')} {...{ auth }} />
        )}
      />
      <Route
        path="/resetPassword"
        component={({ location }) => (
          <Login
            splat={location.pathname.replace('/', '')}
            token={location.query.token}
            {...{ auth }}
          />
        )}
      />
      <Route path="*" component={NoMatch} />
    </Route>
  );
};
