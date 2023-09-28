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

import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import {
  getPasswordResetAvatarUrl,
  getResetPasswordInfo,
  resetPasswordComplete,
  resetPasswordRequest,
} from '../../api/login';
import history from '../../services/History';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

class PasswordRecovery extends Component {
  constructor(props) {
    super(props);

    this.state = {
      err: '',
      pending: false,
      resetEmailSent: false,
      form: {},
      invalidToken: false,
    };
  }

  componentDidMount() {
    const { token } = this.props;
    const resetPassword = !!token;

    if (resetPassword) {
      this.getUserData().catch(({ data }) => {
        this.setState({
          err: data.message,
          invalidToken: true,
        });
      });
    }

    this.focusField.focus();
  }

  getUserData = () => {
    const { token } = this.props;
    const { form } = this.state;

    return getResetPasswordInfo(token).then(({ data }) => {
      this.setState({
        form: {
          ...form,
          email: data.email,
          fullname: data.fullname,
        },
      });
    });
  };

  redirectToLogin = () => {
    history.push('/login');
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

  handleSubmit = (e) => {
    e.preventDefault();

    const { token, onResetOk } = this.props;
    const { form, resetEmailSent } = this.state;
    const resetPassword = !!token;

    if (resetEmailSent) {
      return;
    }

    if (resetPassword) {
      const { password, re_password } = this.state.form;

      if (password !== re_password) {
        this.setState({
          err: counterpart.translate(
            'forgotPassword.error.retypedNewPasswordNotMatch'
          ),
        });
      } else {
        // add email (so we need to save it when loading page)
        this.setState(
          {
            pending: true,
            err: '',
          },
          () => {
            resetPasswordComplete(token, {
              email: form.email,
              password: form.password,
              token,
            })
              .then((response) => {
                onResetOk(response);
              })
              .catch((err) => {
                this.setState({
                  err: err.data
                    ? err.data.message
                    : counterpart.translate('login.error.fallback'),
                  pending: false,
                });
              });
          }
        );
      }
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
            .catch((error) => {
              this.setState({ err: error.data.message, pending: false });
            });
        }
      );
    }
  };

  renderForgottenPasswordForm = () => {
    const { pending, err, resetEmailSent } = this.state;

    if (resetEmailSent) {
      return (
        <div>
          <div className="form-control-label instruction-sent">
            {counterpart.translate('forgotPassword.resetCodeSent.caption')}
          </div>
        </div>
      );
    }

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
            onChange={(e) => this.handleChange(e, 'email')}
            className={classnames('input-primary input-block', {
              'input-error': err,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={(c) => (this.focusField = c)}
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
            type="password"
            onChange={(e) => this.handleChange(e, 'password')}
            name="password"
            className={classnames('input-primary input-block', {
              'input-error': err,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={(c) => (this.focusField = c)}
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
            onChange={(e) => this.handleChange(e, 're_password')}
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
          />
        </div>
      </div>
    );
  };

  renderInvalid = () => {
    const { err } = this.state;
    const buttonMessage = 'Return to login';

    return (
      <div>
        <div>{err && <div className="input-error">{err}</div>}</div>
        <div className="mt-2">
          <button
            className="btn btn-sm btn-block btn-meta-success"
            type="button"
            onClick={this.redirectToLogin}
          >
            {buttonMessage}
          </button>
        </div>
      </div>
    );
  };

  renderContent = () => {
    const { token } = this.props;
    const { pending, resetEmailSent, form } = this.state;
    const resetPassword = !!token;
    const buttonMessage = resetPassword
      ? counterpart.translate('forgotPassword.changePassword.caption')
      : counterpart.translate('forgotPassword.sendResetCode.caption');
    const avatarSrc = getPasswordResetAvatarUrl(token);

    return (
      <div>
        {resetPassword && avatarSrc && (
          <div className="text-center">
            <img src={avatarSrc} className="avatar mt-2 mb-2" alt="avatar" />
          </div>
        )}
        {form.fullname && (
          <div className="text-center">
            <span className="user-data">{form.fullname}</span>
          </div>
        )}
        <form ref={(c) => (this.form = c)} onSubmit={this.handleSubmit}>
          {!resetEmailSent && resetPassword
            ? this.renderResetPasswordForm()
            : this.renderForgottenPasswordForm()}

          {!resetEmailSent && (
            <div className="mt-2">
              <button
                className="btn btn-sm btn-block btn-meta-success"
                disabled={pending}
                type="submit"
              >
                {buttonMessage}
              </button>
            </div>
          )}
        </form>
      </div>
    );
  };

  render() {
    const { invalidToken } = this.state;

    return (
      <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" alt="logo" />
        </div>
        <div>{invalidToken ? this.renderInvalid() : this.renderContent()}</div>
      </div>
    );
  }
}

PasswordRecovery.propTypes = {
  path: PropTypes.string.isRequired,
  token: PropTypes.string,
  onResetOk: PropTypes.func,
};

export default PasswordRecovery;
