import React from 'react';
import {
  Route,
  Switch,
  Redirect,
  Router,
} from 'react-router-dom';
import qs from 'qs';
import _ from 'lodash';

import { localLoginRequest } from './api';
// import { clearNotifications, enableTutorial } from './actions/AppActions';
// import { createWindow } from './actions/WindowActions';
// import { setBreadcrumb } from './actions/MenuActions';
import { useAuth } from './hooks/useAuth';
import history from './services/History';

import ChildRoutes from './routes/ChildRoutes';

// import Translation from './components/Translation';
// import BlankPage from './components/BlankPage';
// import Dashboard from './containers/Dashboard.js';
// import Calendar from './containers/Calendar';
// import InboxAll from './containers/InboxAll.js';
import Login from './containers/Login.js';
// import NavigationTree from './containers/NavigationTree.js';
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

const PrivateRoute = (props) => {
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();
  // const authRequired = (nextState, replace, callback) => {
  //   hasTutorial =
  //     nextState &&
  //     nextState.location &&
  //     nextState.location.query &&
  //     typeof nextState.location.query.tutorial !== 'undefined';
  console.log('PrivateRoute: ', props);

  let pathname = '/login';

  if (!loggedIn) {
    console.log('notlogged')
    localLoginRequest().then((resp) => {
      console.log('logindata: ', resp)
      if (resp.data) {
        // dispatch(loginSuccess(auth.auth));
        auth.loginSuccess();
        console.log('logged in: ', props.location.pathname);
        // callback(null, nextState.location.pathname);
      } else {
        console.log('no logindata, redirect to login ')
        //redirect tells that there should be
        //step back in history after login
        // dispatch(push('/login?redirect=true'));
        pathname = '/login?redirect=true';
      }
    });
  } else {
    // if (hasTutorial) {
    //   dispatch(enableTutorial());
    // }
    console.log('PrivateRoute logged: ', props.location)

    if (props.location.pathname !== 'logout') {
      // dispatch(clearNotifications());
      // dispatch(loginSuccess(auth));
      auth.loginSuccess();

      // return null;
    }
    else {
      // return null;
      console.log('pathname not equal logout: ', pathname)
    }
  // callback();
  }
  // };

  return (
    <Route
      {...props}
      render={() =>
        loggedIn ? (
          <ChildRoutes />
        ) : (
          <Redirect
            to={{
              pathname,
            }}
          />
        )
      }
    />
  );
};

// function propsAreEqual(prevProps, nextProps) {
//   const { computedMatch, location: { key } } = prevProps;
//   const { computedMatch: nextComputedMatch, location: { key: nextKey } } = nextProps;

//   // console.log('EQUAL: ', _.isEqual(computedMatch, nextComputedMatch), key, nextKey)

//   if (_.isEqual(computedMatch, nextComputedMatch) && key === nextKey) {
//     return true;
//   }

//   return false;
// }
// // const PrivateRoute = React.memo(RawPrivateRoute, propsAreEqual);

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

export default React.memo(Routes);
