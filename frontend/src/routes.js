import React from 'react';
import { Route, Switch, Redirect, Router } from 'react-router-dom';
import qs from 'qs';
import _ from 'lodash';

import { useAuth } from './hooks/useAuth';
import history from './services/History';
import { getResetPasswordInfo } from './api';
import PrivateRoute from './routes/PrivateRoute';
import TokenRoute from './routes/TokenRoute';

import Translation from './components/Translation';
import Calendar from './containers/Calendar';
import Login from './containers/Login.js';
// import PluginContainer, { pluginWrapper } from './components/PluginContainer';
import PaypalReservationConfirm from './containers/PaypalReservationConfirm.js';

const RawLoginRoute = (props) => {
  const auth = useAuth();
  const { isLoggedIn } = auth;
  const { location, splatPath, token } = props;
  const query = qs.parse(location.search, { ignoreQueryPrefix: true });
  const splat = splatPath ? location.pathname.replace('/', '') : null;

  console.log('<LoginRoute> params: ', isLoggedIn);

  if (isLoggedIn) {
    return <Redirect to="/" />;
  }

  return <Login redirect={query.redirect} {...{ auth, splat, token }} />;
};

function propsAreEqual(prevProps, nextProps) {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  if (_.isEqual(match, nextMatch) && _.isEqual(location, nextLocation)) {
    return true;
  }

  return false;
}
const LoginRoute = React.memo(RawLoginRoute, propsAreEqual);

// function setPluginBreadcrumbHandlers(routesArray, currentBreadcrumb) {
//   routesArray.forEach((route) => {
//     const routeBreadcrumb = [
//       ...currentBreadcrumb,
//       {
//         caption: route.breadcrumb.caption,
//         type: route.breadcrumb.type,
//       },
//     ];

//     route.onEnter = () => dispatch(setBreadcrumb(routeBreadcrumb));

//     if (route.childRoutes) {
//       setPluginBreadcrumbHandlers(route.childRoutes, routeBreadcrumb);
//     }
//   });
// }

const Routes = () => {
  return (
    <Router history={history}>
      <>
        <Switch>
          <Route exact path="/login" component={LoginRoute} />
          <Route path="/token/:tokenId" component={TokenRoute} />
          <Route
            path="/forgottenPassword"
            render={(props) => <LoginRoute {...props} splatPath />}
          />
          <Route
            path="/resetPassword"
            render={({ location, ...rest }) => {
              const query = qs.parse(location.search, {
                ignoreQueryPrefix: true,
              });
              const token = query.token || null;

              if (!token) {
                return <Redirect to={location.pathname} />;
              }

              return getResetPasswordInfo(token).then(() => {
                return Translation.getMessages().then(() => {
                  return (
                    <LoginRoute
                      {...rest}
                      location={location}
                      splatPath
                      token={token}
                    />
                  );
                });
              });
            }}
          />
          <Route
            path="/paypal_confirm"
            component={({ location }) => {
              const query = qs.parse(location.search, {
                ignoreQueryPrefix: true,
              });
              return <PaypalReservationConfirm token={query.token} />;
            }}
          />
          <Route path="/calendar" component={Calendar} />
          <PrivateRoute path="/" />
        </Switch>
      </>
    </Router>
  );
};

export default React.memo(Routes);
