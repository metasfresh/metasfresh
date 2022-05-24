import React from 'react';
import PropTypes from 'prop-types';

import { Shortcut } from '../keyshortcuts';

const blurActiveElement = () => {
  const activeElement = document.activeElement;

  if (activeElement && activeElement.blur) {
    activeElement.blur();
  }
};

const ModalContextShortcuts = ({ done, cancel }) => {
  const doneAction = (event) => {
    event.preventDefault();
    blurActiveElement();
    done && done();
  };

  const cancelAction = (event) => {
    event.preventDefault();
    cancel && cancel();
  };

  return (
    <>
      <Shortcut
        key="OPEN_PRINT_RAPORT"
        name="OPEN_PRINT_RAPORT"
        handler={doneAction}
      />
      <Shortcut key="DONE" name="DONE" handler={doneAction} />
      <Shortcut key="CANCEL" name="CANCEL" handler={cancelAction} />
    </>
  );
};

ModalContextShortcuts.propTypes = {
  done: PropTypes.func,
  cancel: PropTypes.func,
};

export default ModalContextShortcuts;
