import React from 'react';
import { Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';
import qs from 'qs';
import _ from 'lodash';

import { useAuth } from '../hooks/useAuth';
import Login from '../containers/Login.js';

const LoginRoute = (props) => {
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

LoginRoute.propTypes = {
  location: PropTypes.object.isRequired,
  splatPath: PropTypes.bool,
  token: PropTypes.string,
};

export default React.memo(LoginRoute, propsAreEqual);
