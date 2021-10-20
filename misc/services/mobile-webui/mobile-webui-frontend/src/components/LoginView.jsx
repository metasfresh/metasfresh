import React, { useEffect, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';

import { useAuth } from '../hooks/useAuth';
import counterpart from 'counterpart';
import ScreenToaster from './ScreenToaster';
// import OfflineNotifBar from './OfflineNotifBar';
import { toastError } from '../utils/toast';

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

  useEffect(() => {
    const token = Cookies.get('Token');

    if (token) {
      auth.localLogin({ token }).then(() => {
        history.replace(from);
      });
    }
  }, []);

  const changeUsername = (e) => setUsername(e.target.value);
  const changePassword = (e) => setPassword(e.target.value);

  const submitForm = (e) => {
    e.preventDefault();
    if (username && password) {
      auth
        .login(username, password)
        .then(() => history.replace(from))
        .catch((axiosError) => toastError({ axiosError }));
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
                <i className="fas fa-user" />
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
                <i className="fas fa-lock" />
              </span>
            </p>
          </div>
          <div className="field">
            <div className="control">
              <button type="submit" className="button is-medium btn-green" onClick={submitForm}>
                {counterpart.translate('login.submitButton')}
              </button>
            </div>
          </div>
        </form>
      </div>

      {/* <OfflineNotifBar headerKey="login.offlineMsgHeader" captionKey="login.offlineMsgContent" /> */}
      <ScreenToaster />
    </div>
  );
}

export default LoginRoute;
