import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import Moment from 'moment';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import classnames from 'classnames';

import {
  resetPasswordRequest,
  getResetPasswordInfo,
  resetPasswordComplete,
  resetPasswordGetAvatar,
  getUserLang,
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

    getResetPasswordInfo(token).then(({ data }) => {
      this.setState({
        form: {
          ...form,
          email: data.email,
          fullname: data.fullname,
        },
      });
    });
  };

  handleKeyPress = e => {
    if (e.key === 'Enter') {
      this.form.submit();
    }
  };

  handleChange = (e, name) => {
    e.preventDefault();
    const { token } = this.props;

    this.setState(
      {
        err: '',
        form: {
          ...this.state.form,
          [`${name}`]: e.target.value,
        },
      },
      () => {
        const { password, re_password } = this.state.form;

        setTimeout(() => {
          if (token) {
            if (password !== re_password) {
              this.setState({
                err: counterpart.translate(
                  'forgotPassword.error.retypedNewPasswordNotMatch'
                ),
              });
            }
          }
        }, 200);
      }
    );
  };

  handleLoginSuccess = () => {
    const { dispatch } = this.props;

    getUserLang().then(response => {
      Moment.locale(response.data['key']);
      dispatch(push('/'));
    });
  };

  handleSubmit = e => {
    e.preventDefault();

    const { token } = this.props;
    const { form, resetEmailSent } = this.state;
    const resetPassword = token ? true : false;

    if (resetEmailSent) {
      return;
    }

    if (resetPassword) {
      // add email (so we need to save it when loading page)
      this.setState(
        {
          pending: true,
          err: '',
        },
        () => {
          resetPasswordComplete({
            email: form.email,
            password: form.password,
            token,
          })
            .then(() => {
              return this.handleSuccess();
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
    } else {
      this.setState(
        {
          pending: true,
          err: '',
        },
        () => {
          resetPasswordRequest(form)
            .then(() => {
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
            <small>
              {counterpart.translate(
                'forgotPassword.retypeNewPassword.caption'
              )}
            </small>
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
              <img src={`data:image/*;base64,${avatarSrc}`} className="avatar mt-2 mb-2" />
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

PasswordRecovery.propTypes = {
  dispatch: PropTypes.func.isRequired,
  path: PropTypes.string.isRequired,
  token: PropTypes.string,
};

export default connect()(PasswordRecovery);
