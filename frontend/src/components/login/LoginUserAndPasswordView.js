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
import PropTypes from 'prop-types';
import OAuth2LoginButtons from './OAuth2LoginButtons';

export const LoginUserAndPasswordView = ({
  disabled,
  error,
  clearError,
  onSubmit,
  onForgotPasswordClicked,
}) => {
  const usernameRef = useRef(null);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const isEnabled = !disabled;
  const isSubmitEnabled = isEnabled && !!username && !!password;

  useEffect(() => {
    if (isEnabled) {
      usernameRef.current.focus();
    }
  }, [isEnabled]);

  const fireOnSubmit = () => {
    if (!isSubmitEnabled) return;

    onSubmit({ username, password });
  };

  return (
    <div
      onKeyUp={(e) => {
        if (isSubmitEnabled && e.key === 'Enter') {
          fireOnSubmit();
        }
      }}
    >
      {error && <div className="input-error">{error}</div>}
      <div>
        <div className="form-control-label">
          <small>{counterpart.translate('login.caption')}</small>
        </div>
        <input
          ref={usernameRef}
          type="text"
          value={username}
          onChange={(e) => {
            e.preventDefault();
            clearError();
            setUsername(e.target.value);
          }}
          name="username"
          className={classnames('input-primary input-block', {
            'input-error': error,
            'input-disabled': !isEnabled,
          })}
          disabled={!isEnabled}
        />
      </div>
      <div>
        <div className="form-control-label">
          <small>{counterpart.translate('login.password.caption')}</small>
        </div>
        <input
          type="password"
          name="password"
          value={password}
          onChange={(e) => {
            e.preventDefault();
            clearError();
            setPassword(e.target.value);
          }}
          className={classnames('input-primary input-block', {
            'input-error': error,
            'input-disabled': !isEnabled,
          })}
          disabled={!isEnabled}
        />
      </div>
      <div className="mt-2">
        <button
          className="btn btn-sm btn-block btn-meta-success"
          onClick={fireOnSubmit}
          disabled={!isSubmitEnabled}
        >
          {counterpart.translate('login.callToAction')}
        </button>
      </div>
      <div className="mt-2 text-center">
        <a className="forgot-password-link" onClick={onForgotPasswordClicked}>
          {counterpart.translate('login.forgotPassword.caption')}
        </a>
      </div>
      <div className="mt-2 text-center">
        <OAuth2LoginButtons disabled={!isEnabled} />
      </div>
    </div>
  );
};

LoginUserAndPasswordView.propTypes = {
  disabled: PropTypes.bool,
  error: PropTypes.string,
  clearError: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
  onForgotPasswordClicked: PropTypes.func.isRequired,
};
