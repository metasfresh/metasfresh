import React, { useState, useEffect } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';

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
  const [error, setError] = useState(null);
  const { from } = location.state || { from: { pathname: '/' } };

  useEffect(() => {
    const tokenCookie = Cookies.get('Token');

    if (tokenCookie) {
      auth.localLogin(tokenCookie).then(() => {
        history.replace(from);
      });
    }
  }, []);

  const changeUsername = (e) => setUsername(e.target.value);
  const changePassword = (e) => setPassword(e.target.value);

  const submitForm = (e) => {
    e.preventDefault();
    setError(null);

    if (username && password) {
      auth
        .login(username, password)
        .then(() => {
          history.replace(from);
        })
        .catch((err) => {
          setError(err);
        });
    }
  };

  return (
    <div className="section login_screen">
      <div className="container p-6 login-view">
        <form>
          <div className="field">
            <p className="control has-icons-left">
              <input
                className="input is-medium"
                type="text"
                name="username"
                value={username}
                onChange={changeUsername}
              />
              <span className="icon is-small is-left">
                <i className="fas fa-user"></i>
              </span>
            </p>
          </div>
          <div className="field">
            <p className="control has-icons-left">
              <input
                className="input is-medium"
                id="password"
                type="password"
                name="password"
                value={password}
                onChange={changePassword}
              />
              <span className="icon is-small is-left">
                <i className="fas fa-lock"></i>
              </span>
            </p>
          </div>
          <div className="field">
            <div className="control">
              <button type="submit" className="button is-medium is-success is-green-bg" onClick={submitForm}>
                Log in
              </button>
            </div>
            {error ? <p className="help is-danger">{error}</p> : null}
          </div>
        </form>
      </div>
    </div>
  );
}

export default LoginRoute;
