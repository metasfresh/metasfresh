import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import _ from 'lodash';

import { localLoginRequest } from '../api';
// import { clearNotifications, enableTutorial } from './actions/AppActions';
import { useAuth } from '../hooks/useAuth';
import ChildRoutes from './ChildRoutes';

const PrivateRoute = (props) => {
  const auth = useAuth();
  const loggedIn = auth.isLoggedIn();
  // const authRequired = (nextState, replace, callback) => {
  //   hasTutorial =
  //     nextState &&
  //     nextState.location &&
  //     nextState.location.query &&
  //     typeof nextState.location.query.tutorial !== 'undefined';
  // console.log('PrivateRoute: ', props);

  let pathname = '/login';

  if (!loggedIn) {
    console.log('PrivateRoute notlogged')
    localLoginRequest().then((resp) => {
      console.log('logindata: ', resp)
      if (resp.data) {
        auth.loginSuccess();
        console.log('logged in: ', props.location.pathname);
        // callback(null, nextState.location.pathname);
      } else {
        console.log('no logindata, redirect to login ')
        //redirect tells that there should be
        //step back in history after login
        pathname = '/login?redirect=true';
      }
    });
  } else {
    // if (hasTutorial) {
    //   dispatch(enableTutorial());
    // }
    console.log('PrivateRoute logged: ', props.location, pathname)

    if (props.location.pathname !== '/logout') {
      console.log('PrivateRoute clearNotifications')
      // dispatch(clearNotifications());
      auth.loginSuccess();

      // return null;
    } else {
      // return null;
      console.log('PrivateRoute pathname not equal logout: ', pathname)
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

function propsAreEqual(prevProps, nextProps) {
  const {
    computedMatch,
    location: { key },
  } = prevProps;
  const {
    computedMatch: nextComputedMatch,
    location: { key: nextKey },
  } = nextProps;

  if (_.isEqual(computedMatch, nextComputedMatch) && key === nextKey) {
    return true;
  }

  return false;
}
export default React.memo(PrivateRoute, propsAreEqual);
