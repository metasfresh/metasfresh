import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Shortcut } from '../keyshortcuts';

const TableFilterContextShortcuts = ({ shortcutActions }) => {
  const [shortcutsExtended, setShortcutsExtended] = useState(false);

  useEffect(() => {
    setShortcutsExtended(true);
  }, []);

  return (
    <>
      {shortcutsExtended &&
        shortcutActions.map((shortcut) => (
          <Shortcut
            key={shortcut.shortcut}
            shortcut={shortcut.shortcut}
            name={shortcut.name}
            handler={shortcut.handler}
          />
        ))}
    </>
  );
};

TableFilterContextShortcuts.propTypes = {
  shortcutActions: PropTypes.array,
};

export default TableFilterContextShortcuts;
