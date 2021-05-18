import React from 'react';
import { Route, Switch, Router } from 'react-router-dom';
import qs from 'qs';

import history from './services/History';
import PrivateRoute from './routes/PrivateRoute';
import TokenRoute from './routes/TokenRoute';
import LoginRoute from './routes/LoginRoute';
import ResetPasswordRoute from './routes/ResetPasswordRoute';

import Calendar from './containers/Calendar';
// import PluginContainer, { pluginWrapper } from './components/PluginContainer';
import PaypalReservationConfirm from './containers/PaypalReservationConfirm.js';

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
          <Route path="/resetPassword" component={ResetPasswordRoute} />
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
