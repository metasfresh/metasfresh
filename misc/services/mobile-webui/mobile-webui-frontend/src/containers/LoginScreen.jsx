import React, { useEffect, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';

import { useAuth } from '../hooks/useAuth';
import counterpart from 'counterpart';
import ScreenToaster from '../components/ScreenToaster';
import { toastError } from '../utils/toast';

import LogoHeader from '../components/screenHeaders/LogoHeader';

const LoginScreen = () => {
  const history = useHistory();
  const location = useLocation();
  const auth = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { from } = location.state || { from: { pathname: '/' } };

  useEffect(() => {
    const token = Cookies.get('Token');

    if (token) {
      auth.localLogin({ token }).then(() => history.replace(from));
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
    <div>
      <LogoHeader />
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
      </div>
      <ScreenToaster />
    </div>
  );
};

export default LoginScreen;
