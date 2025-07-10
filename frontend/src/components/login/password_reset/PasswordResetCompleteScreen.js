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
import logo from '../../../assets/images/metasfresh_logo_green_thumb.png';
import { useHistory } from 'react-router-dom';
import { trl } from '../../../utils/locale';
import PropTypes from 'prop-types';
import { isBlank } from '../../../utils';
import { usePasswordResetToken } from './usePasswordResetToken';

const PasswordResetCompleteScreen = () => {
  const history = useHistory();
  const [error, setError] = useState('');
  const [pending, setPending] = useState(false);

  const {
    fullname,
    avatarSrc,
    isInvalidToken,
    invalidTokenReason,
    changePasswordTo,
  } = usePasswordResetToken();

  const handleSubmit = ({ newPassword, newPassword2 }) => {
    if (newPassword !== newPassword2) {
      setError(
        counterpart.translate('forgotPassword.error.retypedNewPasswordNotMatch')
      );
    } else {
      setPending(true);
      setError('');
      changePasswordTo({ newPassword })
        .then(() => history.push('/'))
        .catch((error) =>
          setError(
            error?.data?.message ??
              counterpart.translate('login.error.fallback')
          )
        )
        .finally(() => setPending(false));
    }
  };

  return (
    <div className="fullscreen">
      <div className="login-container">
        <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
          <div className="text-center">
            <img src={logo} className="header-logo mt-2 mb-2" alt="logo" />
          </div>
          <div>
            {isInvalidToken ? (
              <InvalidTokenPanel error={invalidTokenReason} />
            ) : (
              <PasswordResetCompleteForm
                avatarSrc={avatarSrc}
                fullname={fullname}
                disabled={pending}
                error={error}
                onSubmit={handleSubmit}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PasswordResetCompleteScreen;

//
//
//
// ------------------------------------------
//
//
//

const PasswordResetCompleteForm = ({
  avatarSrc,
  fullname,
  disabled: disabledParam,
  error,
  onSubmit,
}) => {
  const focusFieldRef = useRef();
  const [newPassword, setNewPassword] = useState('');
  const [newPassword2, setNewPassword2] = useState('');

  const isValid = !isBlank(newPassword) && !isBlank(newPassword2);
  //&& newPassword !== newPassword2 // do not validate this here, we will validate it on submit

  const isEnabled = !disabledParam;
  const isSubmitEnabled = isEnabled && isValid;

  useEffect(() => {
    focusFieldRef?.current?.focus();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!isSubmitEnabled) return;
    return onSubmit({ newPassword, newPassword2 });
  };

  return (
    <div>
      {avatarSrc && (
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
        <div>
          {error && <div className="input-error">{error}</div>}
          <div>
            <div className="form-control-label">
              <small>
                {counterpart.translate('forgotPassword.newPassword.caption')}
              </small>
            </div>
            <input
              ref={focusFieldRef}
              type="password"
              name="newPassword"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className={classnames('input-primary input-block', {
                'input-error': error,
                'input-disabled': !isEnabled,
              })}
              disabled={!isEnabled}
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
              value={newPassword2}
              onChange={(e) => setNewPassword2(e.target.value)}
              className={classnames('input-primary input-block', {
                'input-disabled': !isEnabled,
              })}
              disabled={!isEnabled}
            />
          </div>
        </div>

        <div className="mt-2">
          <button
            className="btn btn-sm btn-block btn-meta-success"
            disabled={!isSubmitEnabled}
            type="submit"
          >
            {trl('forgotPassword.changePassword.caption')}
          </button>
        </div>
      </form>
    </div>
  );
};
PasswordResetCompleteForm.propTypes = {
  avatarSrc: PropTypes.string,
  fullname: PropTypes.string,
  disabled: PropTypes.bool,
  error: PropTypes.string,
  onSubmit: PropTypes.func.isRequired,
};

//
//
// ------------------------------------------
//
//
//

const InvalidTokenPanel = ({ error }) => {
  const history = useHistory();

  return (
    <div>
      <div>{error && <div className="input-error">{error}</div>}</div>
      <div className="mt-2">
        <button
          className="btn btn-sm btn-block btn-meta-success"
          type="button"
          onClick={() => history.push('/login')}
        >
          Return to login
        </button>
      </div>
    </div>
  );
};
InvalidTokenPanel.propTypes = {
  error: PropTypes.string.isRequired,
};
