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

export const Login2FAView = ({ disabled, error, clearError, onSubmit }) => {
  const inputRef = useRef(null);
  const [code, setCode] = useState('');

  const isEnabled = !disabled;
  const isSubmitEnabled = isEnabled && !!code;

  useEffect(() => {
    if (isEnabled) {
      inputRef.current.focus();
      inputRef.current.select();
    }
  }, [isEnabled]);

  const fireOnSubmit = (code) => {
    if (!isSubmitEnabled) return;

    onSubmit({ code });
  };

  return (
    <div
      onKeyUp={(e) => {
        if (isEnabled && e.key === 'Enter') {
          fireOnSubmit(code);
        }
      }}
    >
      {error && <div className="input-error">{error}</div>}
      <div>
        <div className="form-control-label">
          <small>{counterpart.translate('login.2FA.caption')}</small>
        </div>
        <input
          ref={inputRef}
          type="text"
          value={code}
          onChange={(e) => {
            const code = e.target.value;
            setCode(code);
            clearError();

            if (code && code.length === 6) {
              fireOnSubmit(code);
            }
          }}
          name="code2FA"
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
          onClick={() => fireOnSubmit(code)}
          disabled={!isSubmitEnabled}
        >
          {counterpart.translate('login.send.caption')}
        </button>
      </div>
    </div>
  );
};

Login2FAView.propTypes = {
  disabled: PropTypes.bool,
  error: PropTypes.string,
  clearError: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
};
