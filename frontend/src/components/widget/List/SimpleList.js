/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

import React from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import { RawList } from './RawList';

/**
 * Simple decoupled list implementation to be used in custom frontend components.
 *
 * Basically it wraps a RawList and implement common sense features and avoids the crap from RawList.
 */
const SimpleList = ({ list, selected, onSelect }) => {
  const listHash = uuidv4();
  const [isFocused, setIsFocused] = React.useState(false);
  const [isToggled, setIsToggled] = React.useState(false);

  // IMPORTANT: we shall send null `listHash` and empty `list` in case the list is not toggled.
  // If we don't do that then the selection dropdown won't be refreshed when the list is changed.
  // Stupid, but true.
  return (
    <RawList
      list={isToggled ? list : []}
      listHash={isToggled ? listHash : null}
      onSelect={onSelect}
      selected={selected}
      isFocused={isFocused}
      isToggled={isToggled}
      onOpenDropdown={() => setIsToggled(true)}
      onCloseDropdown={() => setIsToggled(false)}
      onFocus={() => setIsFocused(true)}
      onBlur={() => setIsFocused(false)}
    />
  );
};

SimpleList.propTypes = {
  list: PropTypes.array,
  selected: PropTypes.object,
  onSelect: PropTypes.func.isRequired,
};

export default SimpleList;
