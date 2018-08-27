import counterpart from 'counterpart';
import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { List } from 'immutable';
import { connect } from 'react-redux';
import { goBack, push } from 'react-router-redux';
import classnames from 'classnames';

import {
  getUserLang,
  localLoginRequest,
  loginCompletionRequest,
  loginRequest,
} from '../../api';
import { loginSuccess } from '../../actions/AppActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawList from '../widget/List/RawList';
import PasswordRecovery from './PasswordRecovery';

class LoginForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      role: '',
      roleSelect: false,
      err: '',
      dropdownToggled: false,
      dropdownFocused: false,
      handleResetSubmit: false,
    };
  }

  componentDidMount() {
    const { path } = this.props;

    if (!path) {
      this.login.focus();
    }
  }

  componentWillUpdate(nextProps, nextState) {
    if (this.roleSelector && nextState.roleSelect) {
      this.roleSelector.instanceRef.dropdown.focus();
    }
  }

  handleKeyPress = e => {
    if (e.key === 'Enter') {
      this.handleLogin();
    }
  };

  handleOnChange = e => {
    e.preventDefault();

    this.setState({
      err: '',
    });
  };

  handleSuccess = () => {
    const { redirect, dispatch } = this.props;

    getUserLang().then(response => {
      //GET language shall always return a result
      Moment.locale(response.data['key']);

      if (redirect) {
        dispatch(goBack());
      } else {
        dispatch(push('/'));
      }
    });
  };

  checkIfAlreadyLogged(err) {
    const { router } = this.context;

    return localLoginRequest().then(response => {
      if (response.data) {
        return router.push('/');
      }

      return Promise.reject(err);
    });
  }

  handleResetOk = response => {
    this.setState(
      {
        handleResetSubmit: true,
        pending: true,
      },
      () => {
        const responsePromise = new Promise(resolve => {
          resolve(response);
        });
        this.handleLoginRequest(responsePromise);
      }
    );
  };

  handleLoginRequest = resp => {
    let request = null;

    if (resp) {
      request = resp;
    } else {
      request = loginRequest(this.login.value, this.passwd.value);
    }

    request
      .then(response => {
        if (response.data.loginComplete) {
          return this.handleSuccess();
        }
        const roles = List(response.data.roles);

        this.setState({
          roleSelect: true,
          roles,
          role: roles.get(0),
        });
      })
      .then(() => {
        this.setState({
          pending: false,
        });
      })
      .catch(err => {
        return this.checkIfAlreadyLogged(err);
      })
      .catch(err => {
        this.setState({
          err: err.response
            ? err.response.data.message
            : counterpart.translate('login.error.fallback'),
          pending: false,
        });
      });
  };

  handleLogin = () => {
    const { dispatch, auth } = this.props;
    const { roleSelect, role } = this.state;

    this.setState(
      {
        pending: true,
      },
      () => {
        if (roleSelect) {
          return loginCompletionRequest(role)
            .then(() => {
              dispatch(loginSuccess(auth));
              this.handleSuccess();
            })
            .catch(err => {
              this.setState({
                err: err.response
                  ? err.response.data.message
                  : counterpart.translate('login.error.fallback'),
                pending: false,
              });
            });
        }

        this.handleLoginRequest();
      }
    );
  };

  handleRoleSelect = option => {
    this.setState({
      role: option,
    });
  };

  handleForgotPassword = () => {
    const { dispatch } = this.props;
    dispatch(push('/forgottenPassword'));
  };

  openDropdown = () => {
    this.setState({
      dropdownToggled: true,
    });
  };

  closeDropdown = () => {
    this.setState({
      dropdownToggled: false,
    });
  };

  onFocus = () => {
    this.setState({
      dropdownFocused: true,
    });
  };

  onBlur = () => {
    this.setState({ dropdownFocused: false });
  };

  render() {
    const {
      roleSelect,
      roles,
      err,
      role,
      pending,
      dropdownToggled,
      dropdownFocused,
      handleResetSubmit,
    } = this.state;
    const { token, path } = this.props;
    const onResetOk = this.handleResetOk;

    if (path && !handleResetSubmit) {
      return <PasswordRecovery {...{ path, token, onResetOk }} />;
    }

    return (
      <div
        className="login-form panel panel-spaced-lg panel-shadowed panel-primary"
        onKeyPress={this.handleKeyPress}
      >
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {roleSelect ? (
          <div>
            <div className="form-control-label">
              <small>{counterpart.translate('login.selectRole.caption')}</small>
            </div>
            <RawList
              ref={c => (this.roleSelector = c)}
              rank="primary"
              list={roles}
              onSelect={option => this.handleRoleSelect(option)}
              selected={role}
              disabled={pending}
              autofocus={true}
              doNotOpenOnFocus={true}
              mandatory={true}
              isToggled={dropdownToggled}
              isFocused={dropdownFocused}
              onOpenDropdown={this.openDropdown}
              onCloseDropdown={this.closeDropdown}
              onFocus={this.onFocus}
              onBlur={this.onBlur}
            />
          </div>
        ) : (
          <div>
            {err && <div className="input-error">{err}</div>}
            <div>
              <div className="form-control-label">
                <small>{counterpart.translate('login.caption')}</small>
              </div>
              <input
                type="text"
                onChange={this.handleOnChange}
                name="username"
                className={classnames('input-primary input-block', {
                  'input-error': err,
                  'input-disabled': pending,
                })}
                disabled={pending}
                ref={c => (this.login = c)}
              />
            </div>
            <div>
              <div className="form-control-label">
                <small>{counterpart.translate('login.password.caption')}</small>
              </div>
              <input
                type="password"
                name="password"
                onChange={this.handleOnChange}
                className={classnames('input-primary input-block', {
                  'input-error': err,
                  'input-disabled': pending,
                })}
                disabled={pending}
                ref={c => (this.passwd = c)}
              />
            </div>
          </div>
        )}
        <div className="mt-2">
          <button
            className="btn btn-sm btn-block btn-meta-success"
            onClick={this.handleLogin}
            disabled={pending}
          >
            {roleSelect
              ? counterpart.translate('login.send.caption')
              : counterpart.translate('login.callToAction')}
          </button>
        </div>
        {!roleSelect && (
          <div className="mt-2 text-center">
            <a
              className="forgot-password-link"
              onClick={this.handleForgotPassword}
            >
              {counterpart.translate('login.forgotPassword.caption')}
            </a>
          </div>
        )}
      </div>
    );
  }
}

LoginForm.propTypes = {
  dispatch: PropTypes.func.isRequired,
  path: PropTypes.string,
  token: PropTypes.string,
};

LoginForm.contextTypes = {
  router: PropTypes.object.isRequired,
};

export default connect()(LoginForm);
