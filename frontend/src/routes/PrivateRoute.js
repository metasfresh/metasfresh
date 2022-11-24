import React, { useEffect, useState } from 'react';
import { Route, useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import _ from 'lodash';
import queryString from 'query-string';

import { clearNotifications, enableTutorial } from '../actions/AppActions';
import { setBreadcrumb } from '../actions/MenuActions';
import { useAuth } from '../hooks/useAuth';
import ChildRoutes from './ChildRoutes';

let hasTutorial = false;

/**
 * @file Functional component.
 * @module routes/ChildRoutes
 * Top-level route restricting access to the application for non-authorised users, and
 * redirecting to the login page if needed.
 */
const PrivateRoute = (props) => {
  const auth = useAuth();
  const dispatch = useDispatch();
  const history = useHistory();
  const { isLoggedIn, authRequestPending } = auth;
  const { location } = props;
  const query = queryString.parse(location.search, { ignoreQueryPrefix: true });
  hasTutorial = query && typeof query.tutorial !== 'undefined';

  const [firstRender, setFirstRender] = useState(true);

  useEffect(() => {
    if (firstRender) {
      setFirstRender(false);
      dispatch(clearNotifications());

      // check if user is not already authenticated via another session
      if (!authRequestPending()) {
        const url = location.pathname;

        auth.checkAuthentication().then((authenticated) => {
          if (!authenticated) {
            auth.setRedirectRoute(url);
            setFirstRender(true);
            history.push('/login');
          } else {
            auth.login();
          }
        });
      }

      if (hasTutorial) {
        dispatch(enableTutorial());
      }
    }

    // clear breadcrumbs on all main paths
    if (location.pathname.indexOf('window') === -1) {
      // make sure we clear the breadcrumbs once we are on the dashboard
      dispatch(setBreadcrumb([]));
    }
  }, [location, isLoggedIn]);

  if (!isLoggedIn || authRequestPending() || firstRender) {
    return null;
  }

  return <Route {...props} component={ChildRoutes} />;
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

PrivateRoute.propTypes = {
  location: PropTypes.object.isRequired,
};

export default React.memo(PrivateRoute, propsAreEqual);
