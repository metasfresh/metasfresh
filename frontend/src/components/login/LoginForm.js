/*
 * #%L
 * me04
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import counterpart from 'counterpart';
import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { connect, useDispatch } from 'react-redux';
import { useHistory, withRouter } from 'react-router-dom';

import { connectionError, loginSuccess } from '../../actions/AppActions';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import PasswordRecovery from './PasswordRecovery';
import { BAD_GATEWAY_ERROR } from '../../constants/Constants';
import {
  checkLoginRequest,
  getLoginStatus,
  login2FA,
  loginCompletionRequest,
  loginRequest,
} from '../../api/login';
import { LoginUserAndPasswordView } from './LoginUserAndPasswordView';
import { Login2FAView } from './Login2FAView';
import { RoleSelectView } from './RoleSelectView';
import { getUserLang } from '../../api/userSession';

const VIEW_USER_AND_PASSWORD = 'userAndPassword';
const VIEW_2FA = '2fa';
const VIEW_SELECT_ROLE = 'selectRole';

const LoginForm = ({ token, path, auth }) => {
  const [currentView, setCurrentView] = useState(VIEW_USER_AND_PASSWORD);
  const [pending, setPending] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isHandleResetSubmit, setIsHandleResetSubmit] = useState(false);
  const [roles, setRoles] = useState([]);

  const history = useHistory();
  const dispatch = useDispatch();

  const isEnabled = !pending;

  const urlParams = new URLSearchParams(window.location.search);
  const oauthStatus = urlParams.get('authStatus');
  const oauthError = urlParams.get('authError');
  const authErrorMessage = urlParams.get('authErrorMessage');
  useEffect(() => {
    if (oauthError) {
      setErrorMessage(authErrorMessage ?? 'Authentication error'); // TODO trl
    } else if (oauthStatus === 'success') {
      setPending(true);
      getLoginStatus()
        .then((response) => {
          console.log('getLoginStatus', { response });
          if (response.availableRoles != null) {
            setRoles(response.availableRoles);
          }

          if (response.status === 'REQUIRES_AUTHENTICATION') {
            setCurrentView(VIEW_USER_AND_PASSWORD);
          } else if (response.status === 'REQUIRES_2FA') {
            setCurrentView(VIEW_2FA);
          } else if (response.status === 'REQUIRES_LOGIN_COMPLETE') {
            setCurrentView(VIEW_SELECT_ROLE);
          } else if (response.status === 'LOGGED_IN') {
            handleLoginComplete();
          }
        })
        .finally(() => setPending(false));
      // TODO check login
    }
  }, [oauthStatus, oauthError, authErrorMessage]);

  const clearError = () => setErrorMessage('');

  const handleLoginComplete = () => {
    getUserLang().then((response) => {
      //GET language shall always return a result
      Moment.locale(response.data['key']);

      // get user session and so on
      dispatch(loginSuccess(auth.auth));

      const redirect = auth.redirectRoute;

      if (redirect) {
        auth.clearRedirectRoute();
        history.push(redirect);
      } else {
        history.push('/');
      }
    });
  };

  /**
   * @summary Used to verify if a user is already logged in. i.e user is authenticated in another tab and we are on the loging form screen.
   */
  const checkIfAlreadyLogged = (axiosError) => {
    console.log('Checking if already logged in...', { axiosError });
    return checkLoginRequest().then((response) => {
      const isLoggedIn = !!response.data;

      if (isLoggedIn) {
        console.log('Already logged in => forwarding to /');
        return history.push('/');
      } else {
        console.log('Not already logged in');
        return Promise.reject(axiosError);
      }
    });
  };

  const handleResetOk = (response) => {
    setIsHandleResetSubmit(true);
    setPending(true);

    handleLogin({
      promise: new Promise((resolve) => resolve(response)),
      setError: setLoginError,
    });
  };

  const setLoginError = (message) => {
    const messageEffective =
      message ?? counterpart.translate('login.error.fallback');

    console.log('setLoginError', { message, messageEffective });

    setErrorMessage(messageEffective);
    setPending(false);
  };

  const show2FAView = () => {
    setCurrentView(VIEW_2FA);
  };

  const showRoleSelectView = (roles) => {
    setCurrentView(VIEW_SELECT_ROLE);
    setRoles(roles);
  };

  const handleLogin = ({ authenticateFunc }) => {
    setPending(true);
    return authenticateFunc()
      .then((response) => {
        const errorType = response.status === 502 ? BAD_GATEWAY_ERROR : '';
        dispatch(connectionError({ errorType }));

        if (response.status !== 200) {
          setErrorMessage(response?.data?.message);
        } else if (response.data.loginComplete) {
          return handleLoginComplete();
        } else if (response.data.requires2FA) {
          show2FAView();
        } else {
          showRoleSelectView(response.data.roles);
        }
      })
      .catch((axiosError) => checkIfAlreadyLogged(axiosError))
      .catch((axiosError) =>
        setErrorMessage(axiosError?.response?.data?.message)
      )
      .finally(() => setPending(false));
  };

  const handleUserAndPasswordLogin = ({ username, password }) => {
    return handleLogin({
      authenticateFunc: () => loginRequest(username, password),
    });
  };

  const handle2FA = ({ code }) => {
    return handleLogin({ authenticateFunc: () => login2FA(code) });
  };

  const handleRoleSelected = ({ role }) => {
    setPending(true);
    return loginCompletionRequest(role)
      .then(() => {
        auth.login();
        handleLoginComplete();
      })
      .catch((err) => {
        setErrorMessage(
          err?.response?.data?.message ??
            counterpart.translate('login.error.fallback')
        );
      })
      .finally(() => setPending(false));
  };

  const handleForgotPassword = () => {
    history.push('/forgottenPassword');
  };

  if (path && !isHandleResetSubmit) {
    return (
      <PasswordRecovery path={path} token={token} onResetOk={handleResetOk} />
    );
  }

  return (
    <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
      <div className="text-center">
        <img src={logo} className="login-logo mt-2 mb-2" alt="logo" />
      </div>
      {currentView === VIEW_USER_AND_PASSWORD && (
        <LoginUserAndPasswordView
          disabled={!isEnabled}
          error={errorMessage}
          clearError={clearError}
          onSubmit={handleUserAndPasswordLogin}
          onForgotPasswordClicked={handleForgotPassword}
        />
      )}
      {currentView === VIEW_2FA && (
        <Login2FAView
          disabled={!isEnabled}
          error={errorMessage}
          clearError={clearError}
          onSubmit={handle2FA}
        />
      )}
      {currentView === VIEW_SELECT_ROLE && (
        <RoleSelectView
          disabled={!isEnabled}
          error={errorMessage}
          roles={roles}
          onSubmit={handleRoleSelected}
        />
      )}
    </div>
  );
};

LoginForm.propTypes = {
  path: PropTypes.string,
  token: PropTypes.string,
  redirect: PropTypes.any,
  auth: PropTypes.object,
  history: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect()(withRouter(LoginForm));
