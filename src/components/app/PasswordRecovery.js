import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { goBack, push } from 'react-router-redux';
import classnames from 'classnames';

import {
  resetPasswordRequest,

} from '../../api';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

export default class PasswordRecovery extends Component {
  constructor(props) {
    super(props);

    this.state = {
      err: '',
      pending: false,
    };
  }

  componentDidMount() {
    this.focusField.focus();
  }

  handleKeyPress = e => {
    console.log('keypressed: ', e.key)
    // if (e.key === 'Enter') {
    //   this.handleLogin();
    // }
  };

  handleChange = (e, name) => {
    e.preventDefault();

    this.setState({
      err: '',
      form: {
        ...this.state.form,
        [`${name}`]: e.target.value,
      },
    });
  };

  handleSubmit = e => {
    e.preventDefault();

    const { path } = this.props;
    const { form } = this.state;
    const resetPassword = path === 'resetPassword' ? true : false;

    console.log('submit');

    this.setState(
      {
        pending: true,
      },
      () => {
        console.log('pending: ', form);
        resetPasswordRequest(form)
          .then(response => {
            console.log('response from reset !: ', response);
          })
          .catch(error => {
            console.log('ERROR ! ', error)
            this.setState({ err: error.message });
          });
      }
    );
  };

  // handleSuccess = () => {
  //   const { redirect, dispatch } = this.props;

  //   getUserLang().then(response => {
  //     //GET language shall always return a result
  //     Moment.locale(response.data['key']);

  //     if (redirect) {
  //       dispatch(goBack());
  //     } else {
  //       dispatch(push('/'));
  //     }
  //   });
  // };

  // handleLogin = () => {
  //   const { dispatch, auth } = this.props;
  //   const { roleSelect, role } = this.state;

  //   this.setState(
  //     {
  //       pending: true,
  //     },
  //     () => {
  //       if (roleSelect) {
  //         return loginCompletionRequest(role)
  //           .then(() => {
  //             dispatch(loginSuccess(auth));
  //             this.handleSuccess();
  //           })
  //           .catch(err => {
  //             this.setState({
  //               err: err.response
  //                 ? err.response.data.message
  //                 : counterpart.translate('login.error.fallback'),
  //               pending: false,
  //             });
  //           });
  //       }

  //       loginRequest(this.login.value, this.passwd.value)
  //         .then(response => {
  //           if (response.data.loginComplete) {
  //             return this.handleSuccess();
  //           }
  //           const roles = List(response.data.roles);

  //           this.setState({
  //             roleSelect: true,
  //             roles,
  //             role: roles.get(0),
  //           });
  //         })
  //         .then(() => {
  //           this.setState({
  //             pending: false,
  //           });
  //         })
  //         .catch(err => {
  //           return this.checkIfAlreadyLogged(err);
  //         })
  //         .catch(err => {
  //           this.setState({
  //             err: err.response
  //               ? err.response.data.message
  //               : counterpart.translate('login.error.fallback'),
  //             pending: false,
  //           });
  //         });
  //     }
  //   );
  // };

  // onFocus = () => {
  //   this.setState({
  //     dropdownFocused: true,
  //   });
  // };

  // onBlur = () => {
  //   this.setState({ dropdownFocused: false });
  // };

  renderForgottenPasswordForm = () => {
    const { pending } = this.props;

    return (
      <div>
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.email.caption')}
            </small>
          </div>
          <input
            type="email"
            name="email"
            onChange={e => this.handleChange(e, 'email')}
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={c => (this.focusField = c)}
          />
        </div>
      </div>
    );
  };

  renderResetPasswordForm = () => {
    const { err, pending } = this.state;

    return (
      <div>
        {err && <div className="input-error">{err}</div>}
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.email.caption')}
            </small>
          </div>
          <input
            type="text"
            onChange={e => this.handleChange(e, 'email')}
            name="username"
            className={classnames('input-primary input-block', {
              'input-error': err,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={c => (this.focusField = c)}
          />
        </div>
        <div>
          <div className="form-control-label">
            <small>{counterpart.translate('login.password.caption')}</small>
          </div>
          <input
            type="password"
            name="password"
            onChange={e => this.handleChange(e, 'password')}
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
          />
        </div>
      </div>
    );
  };

  render() {
    const { path } = this.props;
    const { pending } = this.state;
    const resetPassword = path === 'resetPassword' ? true : false;

    return (
      <div
        className="login-form panel panel-spaced-lg panel-shadowed panel-primary"
        onKeyPress={this.handleKeyPress}
      >
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        <form ref={c => (this.form = c)} onSubmit={this.handleSubmit}>
          {resetPassword
            ? this.renderResetPasswordForm()
            : this.renderForgottenPasswordForm()}
          <div className="mt-2">
            <button
              className="btn btn-sm btn-block btn-meta-success"
              _onClick={this.handleSubmit}
              disabled={pending}
              type="submit"
            >
              {resetPassword
                ? counterpart.translate('forgotPassword.changePassword.caption')
                : counterpart.translate('forgotPassword.sendResetCode.caption')}
            </button>
          </div>
        </form>
      </div>
    );
  }
}

// LoginForm.propTypes = {
//   dispatch: PropTypes.func.isRequired,
// };

// LoginForm.contextTypes = {
//   router: PropTypes.object.isRequired,
// };

// export default connect()(LoginForm);
