import React, { useEffect } from 'react';
import { Route, Redirect, useHistory } from 'react-router-dom';
import _ from 'lodash';

// import { localLoginRequest } from '../api';
// import { clearNotifications, enableTutorial } from './actions/AppActions';
import { useAuth } from '../hooks/useAuth';
import useWhyDidYouUpdate from '../hooks/useWhyDidYouUpdate';
import ChildRoutes from './ChildRoutes';

// let hasTutorial = false;

const PrivateRoute = (props) => {
  const auth = useAuth();
  const { isLoggedIn, authRequestPending } = auth;
  const { location } = props;

  const history = useHistory();

  useEffect(() => {
    return history.listen((location) => {
      console.log(`You changed the page to: ${location.pathname}`);
    });
  }, [history]);

  useWhyDidYouUpdate('PrivateRoute', props);
  // const authRequired = (nextState, replace, callback) => {
  //   hasTutorial =
  //     nextState &&
  //     nextState.location &&
  //     nextState.location.query &&
  //     typeof nextState.location.query.tutorial !== 'undefined';

  // let pathname = location.pathname; //'/login';

  if (authRequestPending) {
    return null;
    // return <Redirect to="/login?redirect=true" />;
  }

  if (!isLoggedIn) {
    auth.checkAuthentication((resp) => {
      if (!resp.data) {
        console.log('PrivateRoute.checkAuthentication redirect')
        // return <Redirect to="/login?redirect=true" />;
        history.push('/login?redirect=true');
        // return null;
      }
    });
  //   localLoginRequest().then((resp) => {
  //     console.log('logindata: ', resp.data)
  //     setAuthRequest(false);

  //     if (resp.data) {
  //       auth.loginSuccess();
  //       console.log('logged in: ', props.location.pathname);
  //       // callback(null, nextState.location.pathname);
  //     } else {
  //       console.log('no logindata, redirect to login ')
  //       //redirect tells that there should be
  //       //step back in history after login
  //       pathname = '/login?redirect=true';
  //     }
  //   });
  // }
    // return null;
  } else {
  //   // if (!authRequest) {
  //     // if (hasTutorial) {
  //     //   dispatch(enableTutorial());
  //     // }
  //     console.log('PrivateRoute logged: ', props.location, pathname, loggedIn, authRequest)

      if (location.pathname !== '/logout') {
        console.log('PrivateRoute clearNotifications')
        // dispatch(clearNotifications());
  //       // auth.loginSuccess();
      } else {
        console.log('PrivateRoute pathname equal logout: ', location.pathname)
      }
  //   // }
  }

  console.log('<PrivateRoute> render: ', props, isLoggedIn, authRequestPending);

  // return (
  //   <Route
  //     {...props}
  //     render={() =>
  //       loggedIn ? (
  //         <ChildRoutes />
  //       ) : (
  //         <Redirect
  //           to="/login"
  //         />
  //       )
  //     }
  //   />
  // );
  return (
    <Route
      {...props}
      render={() =>
        <ChildRoutes />
      }
    />
  );
};

function propsAreEqual(prevProps, nextProps) {
  // console.log('PA propsAreEqual: ', prevProps, nextProps)
  const {
    computedMatch,
    location: { key },
  } = prevProps;
  const {
    computedMatch: nextComputedMatch,
    location: { key: nextKey },
  } = nextProps;

  if (_.isEqual(computedMatch, nextComputedMatch) && key === nextKey) {
    // console.log('Y1')
    return true;
  }
  // if (prevProps.location.pathname === nextProps.location.pathname) {
  //   console.log('Y2')
  //   return true;
  // }
  console.log('PA N');
  return false;
}

const dupa = React.memo(PrivateRoute, propsAreEqual);

PrivateRoute.whyDidYouRender = {
  customName: 'PrivateRoute'
};

export default dupa;
