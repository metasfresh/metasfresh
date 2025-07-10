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

import React, { useEffect, useRef, useState } from 'react';
import counterpart from 'counterpart';
import classnames from 'classnames';

import {
  getPasswordResetAvatarUrl,
  getResetPasswordInfo,
  resetPasswordComplete,
  resetPasswordRequest,
} from '../../api/login';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import { useHistory } from 'react-router-dom';

const PasswordRecovery = () => {
  const history = useHistory();
  const focusFieldRef = useRef();
  const [error, setError] = useState('');
  const [pending, setPending] = useState(false);
  const [isResetEmailSent, setIsResetEmailSent] = useState(false);
  const [form, setForm] = useState({});
  const [isInvalidToken, setIsInvalidToken] = useState(false);

  const urlParams = new URLSearchParams(window.location.search);
  const token = urlParams.get('token');

  const isShowNewPasswordPanel = !!token;

  useEffect(() => {
    if (token) {
      getResetPasswordInfo(token)
        .then(({ data }) => {
          setForm({
            ...form,
            email: data.email,
            fullname: data.fullname,
          });
        })
        .catch(({ data }) => {
          setError(data.message);
          setIsInvalidToken(true);
        });
    }
  }, [token]);

  useEffect(() => {
    focusFieldRef?.current?.focus();
  }, []);

  const redirectToLogin = () => {
    history.push('/login');
  };
  const redirectToHome = () => {
    history.push('/');
  };

  const handleFieldChange = (e, name) => {
    e.preventDefault();

    setError('');
    setForm({
      ...form,
      [`${name}`]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isShowNewPasswordPanel) {
      handleSubmit_NewPasswordPanel();
    } else {
      handleSubmit_EnterEmailPanel();
    }
  };

  const handleSubmit_EnterEmailPanel = () => {
    if (isResetEmailSent) return;

    setPending(true);
    setError('');
    resetPasswordRequest(form)
      .then(() => setIsResetEmailSent(true))
      .catch((error) => setError(error.data.message))
      .finally(() => setPending(false));
  };

  const handleSubmit_NewPasswordPanel = () => {
    const { email, password, re_password } = form;

    if (password !== re_password) {
      setError(
        counterpart.translate('forgotPassword.error.retypedNewPasswordNotMatch')
      );
    } else {
      setPending(true);
      setError('');
      resetPasswordComplete(token, { email, password, token })
        .then(() => redirectToHome())
        .catch((error) =>
          setError(
            error?.data?.message ??
              counterpart.translate('login.error.fallback')
          )
        )
        .finally(() => setPending(false));
    }
  };

  const renderEnterEmailPanel = () => {
    if (isResetEmailSent) {
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
        {error && <div className="input-error">{error}</div>}
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.email.caption')}
            </small>
          </div>
          <input
            type="email"
            name="email"
            onChange={(e) => handleFieldChange(e, 'email')}
            className={classnames('input-primary input-block', {
              'input-error': error,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={focusFieldRef}
          />
        </div>
      </div>
    );
  };

  const renderNewPasswordPanel = () => {
    return (
      <div>
        {error && <div className="input-error">{error}</div>}
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.newPassword.caption')}
            </small>
          </div>
          <input
            type="password"
            onChange={(e) => handleFieldChange(e, 'password')}
            name="newPassword"
            className={classnames('input-primary input-block', {
              'input-error': error,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={focusFieldRef}
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
            name="newPassword2"
            onChange={(e) => handleFieldChange(e, 're_password')}
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
          />
        </div>
      </div>
    );
  };

  const renderInvalidPanel = () => {
    return (
      <div>
        <div>{error && <div className="input-error">{error}</div>}</div>
        <div className="mt-2">
          <button
            className="btn btn-sm btn-block btn-meta-success"
            type="button"
            onClick={redirectToLogin}
          >
            Return to login
          </button>
        </div>
      </div>
    );
  };

  const renderContentPanel = () => {
    const { fullname } = form;
    const buttonMessage = isShowNewPasswordPanel
      ? counterpart.translate('forgotPassword.changePassword.caption')
      : counterpart.translate('forgotPassword.sendResetCode.caption');
    const avatarSrc = getPasswordResetAvatarUrl(token);

    return (
      <div>
        {isShowNewPasswordPanel && avatarSrc && (
          <div className="text-center">
            <img src={avatarSrc} className="avatar mt-2 mb-2" alt="avatar" />
          </div>
        )}
        {fullname && (
          <div className="text-center">
            <span className="user-data">{fullname}</span>
          </div>
        )}
        <form onSubmit={handleSubmit}>
          {!isResetEmailSent && isShowNewPasswordPanel
            ? renderNewPasswordPanel()
            : renderEnterEmailPanel()}

          {!isResetEmailSent && (
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

  return (
    <div className="fullscreen">
      <div className="login-container">
        <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
          <div className="text-center">
            <img src={logo} className="header-logo mt-2 mb-2" alt="logo" />
          </div>
          <div>
            {isInvalidToken ? renderInvalidPanel() : renderContentPanel()}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PasswordRecovery;

//
//
// ------------------------------------------
//
//
//
