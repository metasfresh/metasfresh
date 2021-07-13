import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Shortcut } from '../keyshortcuts';

/**
 * @file Class based component.
 * @module ModalContextShortcuts
 * @extends Component
 */
class ModalContextShortcuts extends Component {
  doneAction = (event) => {
    event.preventDefault();

    const { visibleFilter, done } = this.props;

    this.blurActiveElement();
    done && done();

    // if filter is displayed, apply shortcut to filter first
    if (visibleFilter) {
      return true;
    }

    return true;
  };

  handlers = {
    OPEN_PRINT_RAPORT: (event) => this.doneAction(event),
    DONE: (event) => this.doneAction(event),
    CANCEL: (event) => {
      event.preventDefault();

      this.props.cancel && this.props.cancel();
    },
  };

  /**
   * @method blurActiveElement
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  blurActiveElement = () => {
    const activeElement = document.activeElement;

    if (activeElement && activeElement.blur) {
      activeElement.blur();
    }
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return [
      <Shortcut
        key="OPEN_PRINT_RAPORT"
        name="OPEN_PRINT_RAPORT"
        handler={this.handlers.DONE}
      />,
      <Shortcut key="DONE" name="DONE" handler={this.handlers.DONE} />,
      <Shortcut key="CANCEL" name="CANCEL" handler={this.handlers.CANCEL} />,
    ];
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} [visibleFilter]
 * @prop {string} [done]
 * @prop {func} [cancel]
 * @todo Check props. Which proptype? Required or optional?
 */
ModalContextShortcuts.propTypes = {
  visibleFilter: PropTypes.bool,
  done: PropTypes.func,
  cancel: PropTypes.func,
};

export default ModalContextShortcuts;
