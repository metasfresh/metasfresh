import React from 'react';
import { Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';
import _ from 'lodash';

import { useAuth } from '../hooks/useAuth';
import Login from '../containers/Login.js';

/**
 * @file Functional component.
 * @module routes/ChildRoutes
 * Route handling login and showing login form
 */
const LoginRoute = (props) => {
  const auth = useAuth();
  const { isLoggedIn } = auth;
  const { location, splatPath, token } = props;
  const splat = splatPath ? location.pathname.replace('/', '') : null;

  if (isLoggedIn) {
    return <Redirect to="/" />;
  }

  return <Login {...{ auth, splat, token }} />;
};

function propsAreEqual(prevProps, nextProps) {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  if (_.isEqual(match, nextMatch) && _.isEqual(location, nextLocation)) {
    return true;
  }

  return false;
}

LoginRoute.propTypes = {
  location: PropTypes.object.isRequired,
  splatPath: PropTypes.bool,
  token: PropTypes.string,
};

export default React.memo(LoginRoute, propsAreEqual);
