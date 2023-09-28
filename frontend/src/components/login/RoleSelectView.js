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
import RawList from '../widget/List/RawList';
import PropTypes from 'prop-types';

export const RoleSelectView = ({ roles, onSubmit }) => {
  const roleRef = useRef(null);
  const [pending, setPending] = useState(false);
  const [role, setRole] = useState(null);
  const [dropdownToggled, setDropdownToggled] = useState(false);
  const [dropdownFocused, setDropdownFocused] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    roleRef.current?.instanceRef?.dropdown?.focus();
  }, []);

  useEffect(() => {
    setRole(roles?.[0]);
  }, [roles]);

  const fireOnSubmit = (role) => {
    setRole(role);

    setPending(true);
    onSubmit({
      role,
      setError: (errorMsg) => {
        setError(errorMsg);
        setPending(false);
      },
    });
  };

  return (
    <div
      onKeyUp={(e) => {
        if (!pending && e.key === 'Enter') {
          fireOnSubmit(role);
        }
      }}
    >
      {error && <div className="input-error">{error}</div>}
      <div className="form-control-label">
        <small>{counterpart.translate('login.selectRole.caption')}</small>
      </div>
      <RawList
        ref={roleRef}
        rank="primary"
        list={roles}
        onSelect={(option) => fireOnSubmit(option)}
        selected={role}
        disabled={pending}
        autofocus={true}
        doNotOpenOnFocus={true}
        mandatory={true}
        isToggled={dropdownToggled}
        isFocused={dropdownFocused}
        onOpenDropdown={() => setDropdownToggled(true)}
        onCloseDropdown={() => setDropdownToggled(false)}
        onFocus={() => setDropdownFocused(true)}
        onBlur={() => setDropdownFocused(false)}
      />
      <div className="mt-2">
        <button
          className="btn btn-sm btn-block btn-meta-success"
          onClick={fireOnSubmit}
          disabled={pending}
        >
          {counterpart.translate('login.send.caption')}
        </button>
      </div>
    </div>
  );
};

RoleSelectView.propTypes = {
  roles: PropTypes.array.isRequired,
  onSubmit: PropTypes.func.isRequired,
};
