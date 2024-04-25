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
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';

import { connectionError, loginSuccess } from '../../actions/AppActions';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import PasswordRecovery from './PasswordRecovery';
import { BAD_GATEWAY_ERROR } from '../../constants/Constants';
import {
  checkLoginRequest,
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

class LoginForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentView: VIEW_USER_AND_PASSWORD,
      err: '',
      handleResetSubmit: false,
    };
  }

  handleSuccess = () => {
    const { auth, history, dispatch } = this.props;

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
   * @method checkIfAlreadyLogged
   * @summary Used to verify if a user is already logged in. i.e user is authenticated in another tab and we are on the loging form screen.
   * @param {*} axiosError
   */
  checkIfAlreadyLogged(axiosError) {
    const { history } = this.props;

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
  }

  handleResetOk = (response) => {
    this.setState(
      {
        handleResetSubmit: true,
        pending: true,
      },
      () => {
        this.afterLoginRequest({
          promise: new Promise((resolve) => resolve(response)),
          setError: this.setLoginError,
        });
      }
    );
  };

  setLoginError = (message) => {
    const messageEffective =
      message || counterpart.translate('login.error.fallback');

    console.log('setLoginError', { message, messageEffective });

    this.setState({
      err: messageEffective,
      pending: false,
    });
  };

  show2FAView = () => {
    this.setState({ currentView: VIEW_2FA });
  };

  showRoleSelectView = (roles) => {
    this.setState({
      currentView: VIEW_SELECT_ROLE,
      dropdownFocused: true, // directly focus the role dropdown
      roles,
    });
  };

  afterLoginRequest = ({ promise, setError }) => {
    return promise
      .then((response) => {
        const errorType = response.status === 502 ? BAD_GATEWAY_ERROR : '';
        this.props.dispatch(connectionError({ errorType }));

        if (response.status !== 200) {
          setError(response?.data?.message);
        } else if (response.data.loginComplete) {
          return this.handleSuccess();
        } else if (response.data.requires2FA) {
          this.show2FAView();
        } else {
          this.showRoleSelectView(response.data.roles);
        }
      })
      .catch((axiosError) => this.checkIfAlreadyLogged(axiosError))
      .catch((axiosError) => setError(axiosError?.response?.data?.message));
  };

  handleUserAndPasswordLogin = ({ username, password, setError }) => {
    const promise = loginRequest(username, password);
    return this.afterLoginRequest({ promise, setError });
  };

  handle2FA = ({ code, setError }) => {
    const promise = login2FA(code);
    return this.afterLoginRequest({ promise, setError });
  };

  handleRoleSelected = ({ role, setError }) => {
    const { auth } = this.props;

    return loginCompletionRequest(role)
      .then(() => {
        auth.login();
        this.handleSuccess();
      })
      .catch((err) => {
        setError(
          err.response
            ? err.response.data.message
            : counterpart.translate('login.error.fallback')
        );
      });
  };

  handleForgotPassword = () => {
    const { history } = this.props;
    history.push('/forgottenPassword');
  };

  render() {
    const { token, path } = this.props;
    const { currentView, handleResetSubmit } = this.state;
    const { roles } = this.state;

    if (path && !handleResetSubmit) {
      return (
        <PasswordRecovery
          path={path}
          token={token}
          onResetOk={this.handleResetOk}
        />
      );
    }

    return (
      <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
        <div className="text-center">
          <img src={logo} className="login-logo mt-2 mb-2" alt="logo" />
        </div>
        {currentView === VIEW_USER_AND_PASSWORD && (
          <LoginUserAndPasswordView
            onSubmit={this.handleUserAndPasswordLogin}
            onForgotPasswordClicked={this.handleForgotPassword}
          />
        )}
        {currentView === VIEW_2FA && <Login2FAView onSubmit={this.handle2FA} />}
        {currentView === VIEW_SELECT_ROLE && (
          <RoleSelectView roles={roles} onSubmit={this.handleRoleSelected} />
        )}
      </div>
    );
  }
}

LoginForm.propTypes = {
  path: PropTypes.string,
  token: PropTypes.string,
  redirect: PropTypes.any,
  auth: PropTypes.object,
  history: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect()(withRouter(LoginForm));
