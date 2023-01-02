import React from 'react';
import { Route, Switch, Router } from 'react-router-dom';
import queryString from 'query-string';

import history from '../services/History';
import PrivateRoute from './PrivateRoute';
import TokenRoute from './TokenRoute';
import LoginRoute from './LoginRoute';
import ResetPasswordRoute from './ResetPasswordRoute';

import Calendar from '../containers/Calendar';
// import PluginContainer, { pluginWrapper } from './components/PluginContainer';
import PaypalReservationConfirm from '../containers/PaypalReservationConfirm.js';

/**
 * this is the part of the application that activates the plugins from the plugins array found in - plugins.js
 * Currently we aren't using any. Due to this we deactivated the code below. (March, 2021). If this is going to
 * change in the future pls kindly activate this part together with the code in App.js and use the react-router v5
 * routes form
 */
/* 
function setPluginBreadcrumbHandlers(routesArray, currentBreadcrumb) {
  routesArray.forEach((route) => {
    const routeBreadcrumb = [
      ...currentBreadcrumb,
      {
        caption: route.breadcrumb.caption,
        type: route.breadcrumb.type,
      },
    ];

    route.onEnter = () => dispatch(setBreadcrumb(routeBreadcrumb));

    if (route.childRoutes) {
      setPluginBreadcrumbHandlers(route.childRoutes, routeBreadcrumb);
    }
  });
}

const getPluginsRoutes = (plugins) => {
  if (plugins.length) {
    const routes = plugins.map((plugin) => {
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
*/

/**
 * @file Functional component.
 * @module Routes
 * @description react-router routing provider
 */
const Routes = () => {
  return (
    <Switch>
      <Route exact path="/login" component={LoginRoute} />
      <Route path="/token/:tokenId" component={TokenRoute} />
      <Route
        path="/forgottenPassword"
        render={(props) => <LoginRoute {...props} splatPath />}
      />
      <Route path="/resetPassword" component={ResetPasswordRoute} />
      <Route
        path="/paypal_confirm"
        component={({ location }) => {
          const query = queryString.parse(location.search, {
            ignoreQueryPrefix: true,
          });
          return <PaypalReservationConfirm token={query.token} />;
        }}
      />
      <Route path="/calendar" component={Calendar} />
      <PrivateRoute path="/" />
    </Switch>
  );
};

const WrappedRoutes = () => {
  return (
    <Router history={history}>
      <Routes />
    </Router>
  );
};

export { Routes };

export default React.memo(WrappedRoutes);
