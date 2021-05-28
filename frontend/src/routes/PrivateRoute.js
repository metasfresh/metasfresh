import React, { useEffect } from 'react';
import { Route, useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import _ from 'lodash';
import qs from 'qs';

import { clearNotifications, enableTutorial } from '../actions/AppActions';
import { setBreadcrumb } from '../actions/MenuActions';
import { useAuth } from '../hooks/useAuth';
import useConstructor from '../hooks/useConstructor';
import useWhyDidYouUpdate from '../hooks/useWhyDidYouUpdate';
import ChildRoutes from './ChildRoutes';

let hasTutorial = false;

const PrivateRoute = (props) => {
  const auth = useAuth();
  const dispatch = useDispatch();
  const history = useHistory();
  const { isLoggedIn, authRequestPending } = auth;
  const { location } = props;
  const query = qs.parse(location.search, { ignoreQueryPrefix: true });
  hasTutorial = query && typeof query.tutorial !== 'undefined';

  useConstructor(() => {
    if (!isLoggedIn && !authRequestPending()) {
      const url = location.pathname;
      auth.checkAuthentication().then((authenticated) => {
        if (!authenticated) {
          auth.setRedirectRoute(url);
          history.push('/login');
        }
      });
    }
  });

  useEffect(() => {
    if (location.pathname === '/') {
      // make sure we clear the breadcrumbs once we are on the dashboard
      dispatch(setBreadcrumb([]));
    }
  }, [location]);

  useWhyDidYouUpdate('PrivateRoute', props);

  if (!isLoggedIn || authRequestPending()) {
    return null;
  }

  if (isLoggedIn) {
    if (hasTutorial) {
      dispatch(enableTutorial());
    }

    if (location.pathname !== '/logout') {
      dispatch(clearNotifications());
    }
  }

  return <Route {...props} render={() => <ChildRoutes />} />;
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
