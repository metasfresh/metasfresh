import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { goBack } from 'react-router-redux';
import classnames from 'classnames';

import {
  resetPasswordRequest,
  getResetPasswordInfo,
  resetPasswordComplete,
  resetPasswordGetAvatar,
} from '../../api';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

class PasswordRecovery extends Component {
  constructor(props) {
    super(props);

    this.state = {
      err: '',
      pending: false,
      resetEmailSent: false,
      avatarSrc: '',
      form: {},
    };
  }

  componentDidMount() {
    const { token } = this.props;
    const resetPassword = token ? true : false;

    if (resetPassword) {
      this.getAvatar();
      this.getUserData();
    }

    this.focusField.focus();
  }

  getAvatar = () => {
    const { token } = this.props;

    resetPasswordGetAvatar(token).then(({ data }) => {
      this.setState({
        avatarSrc: data,
      });
    });
  };

  getUserData = () => {
    const { token } = this.props;
    const { form } = this.state;

    getResetPasswordInfo(token).then(resp => {
      this.setState({
        form: {
          ...form,
          email: resp.data.email,
          fullname: resp.data.fullname
        },
      });
    });
  };

  handleKeyPress = e => {
    // console.log('keypressed: ', e.key)
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

    const { dispatch, token } = this.props;
    const { form, resetEmailSent } = this.state;
    const resetPassword = token ? true : false;

    if (resetEmailSent) {
      return;
    }

    if (resetPassword) {
      // add email (so we need to save it when loading page)
    } else {
      this.setState(
        {
          pending: true,
        },
        () => {
          resetPasswordRequest(form)
            .then(response => {
              this.setState({
                resetEmailSent: true,
                pending: false,
              });
            })
            .catch(error => {
              this.setState({ err: error.data.message, pending: false });
            });
        }
      );
    }
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
    const { pending, err } = this.state;

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
            type="email"
            name="email"
            onChange={e => this.handleChange(e, 'email')}
            className={classnames('input-primary input-block', {
              'input-error': err,
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
              {counterpart.translate('forgotPassword.newPassword.caption')}
            </small>
          </div>
          <input
            type="text"
            onChange={e => this.handleChange(e, 'password')}
            name="password"
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
            name="re_password"
            onChange={e => this.handleChange(e, 're_password')}
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
    const { token } = this.props;
    const { pending, resetEmailSent, avatarSrc, form } = this.state;
    const resetPassword = token ? true : false;
    let buttonMessage = counterpart.translate(
      'forgotPassword.changePassword.caption'
    );

    if (!resetPassword) {
      if (resetEmailSent) {
        buttonMessage = counterpart.translate(
          'forgotPassword.resetCodeSent.caption'
        );
      } else {
        buttonMessage = counterpart.translate(
          'forgotPassword.sendResetCode.caption'
        );
      }
    }

    return (
      <div
        className="login-form panel panel-spaced-lg panel-shadowed panel-primary"
        onKeyPress={this.handleKeyPress}
      >
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {avatarSrc && (
          <div>
            <div className="text-center">
              <img src={avatarSrc} className="avatar mt-2 mb-2" />
            </div>
            <div className="text-center">
              <span className="user-data">{form.fullname}</span>
            </div>
          </div>
        )}
        <form ref={c => (this.form = c)} onSubmit={this.handleSubmit}>
          {!resetEmailSent && resetPassword
            ? this.renderResetPasswordForm()
            : this.renderForgottenPasswordForm()}
          <div className="mt-2">
            <button
              className="btn btn-sm btn-block btn-meta-success"
              disabled={pending}
              type="submit"
            >
              {buttonMessage}
            </button>
          </div>
        </form>
      </div>
    );
  }
}

export default connect()(PasswordRecovery);
