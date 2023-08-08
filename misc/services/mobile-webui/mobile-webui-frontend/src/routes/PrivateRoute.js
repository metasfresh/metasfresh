import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';

import { getIsLoggedInFromState } from '../reducers/appHandler';

function PrivateRoute({ children, ...rest }) {
  const isLoggedIn = useSelector((state) => getIsLoggedInFromState(state));

  return (
    <Route
      {...rest}
      render={({ location }) =>
        isLoggedIn ? children : <Redirect to={{ pathname: '/login', state: { from: location } }} />
      }
    />
  );
}

PrivateRoute.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]),
};

export default PrivateRoute;
