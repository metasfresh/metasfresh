import React from 'react';
import { Route, Switch, Redirect, Router } from 'react-router-dom';
import qs from 'qs';
import _ from 'lodash';

// import { enableTutorial } from './actions/AppActions';
import { useAuth } from './hooks/useAuth';
import history from './services/History';

import PrivateRoute from './routes/PrivateRoute';

// import Translation from './components/Translation';
// import BlankPage from './components/BlankPage';
// import Calendar from './containers/Calendar';
import Login from './containers/Login.js';
// import PluginContainer, { pluginWrapper } from './components/PluginContainer';
// import PaypalReservationConfirm from './containers/PaypalReservationConfirm.js';

// let hasTutorial = false;

const RawLoginRoute = (props) => {
  const auth = useAuth();
  const { location } = props;
  const query = qs.parse(location.search, { ignoreQueryPrefix: true });
  const logged = auth.isLoggedIn();

  console.log('LoginRoute params: ', props, logged);

  return <Login redirect={query.redirect} {...{ auth }} logged={logged} />;
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


// MASTER
/**
 * @method tokenAuthentication
 * @summary - method executed when we authenticate directly by using a `token` without the need to supply a `username` and a `password`
 * @param {object} - tokenId prop given as param to the /token path i.e  /token/xxxxxxx   (`xxxxxxx` will be the value of the tokenId )
 */
// const tokenAuthentication = ({ params: { tokenId } }) => {
//   loginWithToken(tokenId)
//     .then(() => {
//       dispatch(push('/'));
//     })
//     .catch(() => {
//       dispatch(push('/login?redirect=true'));
//     });
// };
//

// TODO
// const onResetEnter = (nextState, replace, callback) => {
//   const token = nextState.location.query.token;

//   if (!token) {
//     callback(null, nextState.location.pathname);
//   }

//   return getResetPasswordInfo(token).then(() => {
//     return Translation.getMessages().then(() => {
//       callback(null, nextState.location.pathname);
//     });
//   });
// };

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
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();

  console.log('Routes render: ', loggedIn);

  return (
    <Router history={history}>
      <>
        <Switch>
          <Route exact path="/login">
            {loggedIn ? <Redirect to="/" /> : <Route component={LoginRoute} />}
          </Route>
          <PrivateRoute path="/" />
        </Switch>
      </>
    </Router>
  );
};
/*
<Route path="/token/:tokenId" onEnter={tokenAuthentication} />
<Route
  path="/forgottenPassword"
  component={({ location }) => (
    <Login splat={location.pathname.replace('/', '')} {...{ auth }} />
  )}
/>
<Route
  path="/resetPassword"
  onEnter={onResetEnter}
  component={({ location }) => (
    <Login
      splat={location.pathname.replace('/', '')}
      token={location.query.token}
      {...{ auth }}
    />
  )}
/>
<Route
  path="/paypal_confirm"
  component={({ location }) => (
    <PaypalReservationConfirm
      token={location.query.token}
      {...{ auth }}
    />
  )}
/>
<Route path="/calendar" component={Calendar} />
<Route path="*" component={NoMatch} />
*/

export default React.memo(Routes);
