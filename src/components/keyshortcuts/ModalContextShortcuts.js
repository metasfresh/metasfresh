import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Shortcut } from '../keyshortcuts';

class ModalContextShortcuts extends Component {
  handlers = {
    DONE: event => {
      event.preventDefault();

      const { visibleFilter, done } = this.props;

      this.blurActiveElement();
      done && done();

      // if filter is displayed, apply shortcut to filter first
      if (visibleFilter) {
        return true;
      }

      return false;
    },
    CANCEL: event => {
      event.preventDefault();

      this.props.cancel && this.props.cancel();
    },
  };

  blurActiveElement = () => {
    const activeElement = document.activeElement;

    if (activeElement && activeElement.blur) {
      activeElement.blur();
    }
  };

  render() {
    return [
      <Shortcut key="DONE" name="DONE" handler={this.handlers.DONE} />,
      <Shortcut key="CANCEL" name="CANCEL" handler={this.handlers.CANCEL} />,
    ];
  }
}

ModalContextShortcuts.propTypes = {
  visibleFilter: PropTypes.bool,
  done: PropTypes.func,
  cancel: PropTypes.func,
};

export default ModalContextShortcuts;
