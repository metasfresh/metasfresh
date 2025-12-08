import React, { useState } from 'react';
import { Route, Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';
import queryString from 'query-string';

import { useAuth } from '../hooks/useAuth';
import useConstructor from '../hooks/useConstructor';
import { getResetPasswordInfo } from '../api/login';
import LoginRoute from './LoginRoute';

import Translation from '../components/Translation';

/**
 * @file Functional component.
 * @module routes/RessetPasswordRoute
 * Route responsible far hnadling resetting password
 */
const ResetPasswordRoute = ({ location, ...rest }) => {
  const [pending, setPending] = useState(true);
  const auth = useAuth();
  const { isLoggedIn } = auth;
  const query = queryString.parse(location.search, {
    ignoreQueryPrefix: true,
  });
  const token = query.token || null;

  useConstructor(() => {
    getResetPasswordInfo(token)
      .then(() => Translation.getMessages())
      .catch((error) => console.log('Got error', error))
      .finally(() => setPending(false));
  });

  if (isLoggedIn) {
    return <Redirect to="/login" />;
  }

  if (!token) {
    return <Redirect to={location.pathname} />;
  }

  return (
    <Route
      {...rest}
      render={(props) =>
        !pending ? (
          <LoginRoute {...props} location={location} splatPath token={token} />
        ) : null
      }
    />
  );
};

ResetPasswordRoute.propTypes = {
  location: PropTypes.object.isRequired,
};

export default React.memo(ResetPasswordRoute);
