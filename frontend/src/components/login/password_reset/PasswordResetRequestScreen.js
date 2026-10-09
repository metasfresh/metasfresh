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

import { resetPasswordRequest } from '../../../api/login';
import logo from '../../../assets/images/metasfresh_logo_green_thumb.png';
import { trl } from '../../../utils/locale';

const PasswordResetRequestScreen = () => {
  const focusFieldRef = useRef();
  const [error, setError] = useState('');
  const [pending, setPending] = useState(false);
  const [email, setEmail] = useState('');
  const [isResetEmailSent, setIsResetEmailSent] = useState(false);

  useEffect(() => {
    focusFieldRef?.current?.focus();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isResetEmailSent) return;

    setPending(true);
    setError('');
    resetPasswordRequest({ email })
      .then(() => setIsResetEmailSent(true))
      .catch((error) => setError(error?.data?.message))
      .finally(() => setPending(false));
  };

  return (
    <div className="fullscreen">
      <div className="login-container">
        <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
          <div className="text-center">
            <img src={logo} className="header-logo mt-2 mb-2" alt="logo" />
          </div>
          <div>
            <form onSubmit={handleSubmit}>
              <div>
                {error && <div className="input-error">{error}</div>}

                {isResetEmailSent && (
                  <div>
                    <div className="form-control-label instruction-sent">
                      {counterpart.translate(
                        'forgotPassword.resetCodeSent.caption'
                      )}
                    </div>
                  </div>
                )}

                {!isResetEmailSent && (
                  <div>
                    <div className="form-control-label">
                      <small>
                        {counterpart.translate('forgotPassword.email.caption')}
                      </small>
                    </div>
                    <input
                      type="email"
                      name="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className={classnames('input-primary input-block', {
                        'input-error': error,
                        'input-disabled': pending,
                      })}
                      disabled={pending}
                      ref={focusFieldRef}
                    />
                  </div>
                )}
              </div>

              {!isResetEmailSent && (
                <div className="mt-2">
                  <button
                    className="btn btn-sm btn-block btn-meta-success"
                    disabled={pending}
                    type="submit"
                  >
                    {trl('forgotPassword.sendResetCode.caption')}
                  </button>
                </div>
              )}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PasswordResetRequestScreen;
