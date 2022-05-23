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
