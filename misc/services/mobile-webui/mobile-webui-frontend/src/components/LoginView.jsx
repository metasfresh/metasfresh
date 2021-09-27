import React, { useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';

import { useAuth } from '../hooks/useAuth';

/**
 * @file Functional component.
 * @module routes
 * Route handling login and showing login form
 */

function LoginRoute() {
  const history = useHistory();
  const location = useLocation();
  const auth = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { from } = location.state || { from: { pathname: '/' } };

  const changeUsername = (e) => setUsername(e.target.value);
  const changePassword = (e) => setPassword(e.target.value);

  const submitForm = (e) => {
    e.preventDefault();

    if (username && password) {
      auth.login(username, password).then(() => {
        history.replace(from);
      });
    }
  };

  return (
    <div>
      <form onSubmit={submitForm}>
        <input type="text" name="username" value={username} onChange={changeUsername} />
        <input type="password" name="password" value={password} onChange={changePassword} />
        <input type="submit" value="Log in" />
      </form>
    </div>
  );
}

export default LoginRoute;
