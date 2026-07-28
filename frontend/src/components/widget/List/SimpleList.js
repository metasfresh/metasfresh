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
  keepFocused = false,
}) => {
  // Start focused (opt-in) so the first click only has to toggle the dropdown open
  // instead of focusing AND toggling in the same tick (which otherwise races the
  // click-outside handler and swallows the first open). Mirrors the sibling Letter
  // picker, which is driven with a persistent focused state and opens on first click.
  const [isFocused, setIsFocused] = useState(keepFocused);
  const [isToggled, setIsToggled] = useState(false);

  // Recompute the hash when the list OR the selected entry's key changes, so RawList's
  // componentDidUpdate re-runs setSelectedValue and re-highlights the applied entry
  // (it gates on listHash !== prevListHash). Depend on `selected?.key` (value), NOT the
  // `selected` object identity: consumers that build `selected` as a fresh object literal
  // each render would otherwise regenerate the hash on every unrelated re-render and
  // reorder an open dropdown under the cursor.
  const listHash = useMemo(() => uuidv4(), [list, selected?.key]);

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
  keepFocused: PropTypes.bool,
};

export default SimpleList;
