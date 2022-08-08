import React, { useMemo, useState } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import RawList from './RawList';

/**
 * Simple decoupled list implementation to be used in custom frontend components.
 *
 * Basically it wraps a RawList and implement common sense features and avoids the crap from RawList.
 */
const SimpleList = ({
  list,
  selected,
  onSelect,
  onOpenDropdown,
  className,
}) => {
  const [isFocused, setIsFocused] = useState(false);
  const [isToggled, setIsToggled] = useState(false);

  const listHash = useMemo(() => uuidv4(), [list]);

  return (
    <RawList
      className={className}
      list={list}
      listHash={listHash}
      onSelect={onSelect}
      selected={selected}
      isFocused={isFocused}
      isToggled={isToggled}
      onOpenDropdown={() => {
        onOpenDropdown?.();
        setIsToggled(true);
      }}
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
  onOpenDropdown: PropTypes.func,
  className: PropTypes.string,
};

export default SimpleList;
