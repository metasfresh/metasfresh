import counterpart from 'counterpart';
import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { goBack, push } from 'react-router-redux';

import {
  getUserLang,
  localLoginRequest,
  loginCompletionRequest,
  loginRequest,
  loginSuccess,
} from '../../actions/AppActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawList from '../widget/List/RawList';

class LoginForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      role: '',
      roleSelect: false,
      err: '',
    };
  }

  componentDidMount() {
    this.login.focus();
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

  handleLogin = () => {
    const { dispatch, auth } = this.props;
    const { roleSelect, role } = this.state;

    this.setState(
      {
        pending: true,
      },
      () => {
        if (roleSelect) {
          return loginCompletionRequest(role).then(() => {
            dispatch(loginSuccess(auth));
            this.handleSuccess();
          });
        }

        loginRequest(this.login.value, this.passwd.value)
          .then(response => {
            if (response.data.loginComplete) {
              return this.handleSuccess();
            }
            const roles = response.data.roles;

            this.setState({
              roleSelect: true,
              roles,
              role: roles[0],
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
      }
    );
  };

  handleRoleSelect = option => {
    this.setState({
      role: option,
    });
  };

  render() {
    const { roleSelect, roles, err, role, pending } = this.state;
    return (
      <div
        className="login-form panel panel-spaced-lg panel-shadowed panel-primary"
        onKeyDown={this.handleKeyPress}
      >
        <div className="text-xs-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {roleSelect ? (
          <div>
            <div className="form-control-label">
              <small>{counterpart.translate('login.selectRole.caption')}</small>
            </div>
            <RawList
              rank="primary"
              list={roles}
              onSelect={option => this.handleRoleSelect(option)}
              selected={role}
              disabled={pending}
              autofocus={true}
              doNotOpenOnFocus={true}
              mandatory={true}
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
                className={
                  'input-primary input-block ' +
                  (err ? 'input-error ' : '') +
                  (pending ? 'input-disabled ' : '')
                }
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
                onChange={this.handleOnChange}
                className={
                  'input-primary input-block ' +
                  (err ? 'input-error ' : '') +
                  (pending ? 'input-disabled ' : '')
                }
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
      </div>
    );
  }
}

LoginForm.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

LoginForm.contextTypes = {
  router: PropTypes.object.isRequired,
};

export default connect()(LoginForm);
