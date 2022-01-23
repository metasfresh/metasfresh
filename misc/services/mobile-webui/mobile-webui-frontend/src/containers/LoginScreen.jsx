import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';

import { useAuth } from '../hooks/useAuth';
import counterpart from 'counterpart';
import ScreenToaster from '../components/ScreenToaster';
import { extractUserFriendlyErrorMessageFromAxiosError } from '../utils/toast';

import LogoHeader from '../components/LogoHeader';

const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loginPending, setLoginPending] = useState(false);

  const history = useHistory();
  const auth = useAuth();
  const location = useLocation();
  const { from } = location.state || { from: { pathname: '/' } };
  const usernameFieldRef = useRef(null);
  useEffect(() => {
    const token = Cookies.get('Token');
    console.log('useEffect: token', token);

    if (token) {
      auth.localLogin({ token }).then(() => history.replace(from));
    } else {
      usernameFieldRef.current.focus();
      usernameFieldRef.current.select();
    }
  }, []);

  const submitForm = (e) => {
    e.preventDefault();

    setLoginPending(true);
    auth
      .login(username, password)
      .then(() => history.replace(from))
      .catch((axiosError) => {
        setLoginPending(false);
        setErrorMessage(extractUserFriendlyErrorMessageFromAxiosError({ axiosError }));
      });
    // .finally(() => setLoginPending(false)); // don't set it here because at this point the component is already unmounted
  };

  return (
    <div className="login-view">
      <LogoHeader />
      <div className="section is-size-5">
        <div className="container px-6">
          <form>
            <p className="help is-danger is-size-6 login-error">{errorMessage}</p>
            <div className="field">
              <p className="control has-icons-left">
                <input
                  className="input is-medium"
                  type="text"
                  id={username}
                  name="username"
                  value={username}
                  autoComplete="username"
                  ref={usernameFieldRef}
                  disabled={loginPending}
                  onInput={(e) => setUsername(e.target.value)}
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
                  id="current-password"
                  type="password"
                  name="password"
                  value={password}
                  autoComplete="current-password"
                  disabled={loginPending}
                  onInput={(e) => setPassword(e.target.value)}
                />
                <span className="icon is-small is-left">
                  <i className="fas fa-lock" />
                </span>
              </p>
            </div>
            <div className="field">
              <div className="control">
                <button
                  type="submit"
                  className="button is-medium btn-green"
                  disabled={loginPending}
                  onClick={submitForm}
                >
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
