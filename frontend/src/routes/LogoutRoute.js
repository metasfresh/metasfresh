import { Redirect, useHistory } from 'react-router-dom';
import React, { useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';

const Logout = () => {
  const auth = useAuth();
  const history = useHistory();
  const loggedIn = auth.isLoggedIn;

  useEffect(() => {
    if (loggedIn) {
      auth.logout().finally(() => history.push('/login'));
    }
  }, [loggedIn]);

  return <Redirect to="/login" />;
};

export default Logout;
