import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Shortcut } from '../keyshortcuts';

/**
 * @file Class based component.
 * @module QuickActionsContextShortcuts
 * @extends Component
 */
export default class QuickActionsContextShortcuts extends Component {
  handlers = {
    QUICK_ACTION_POS: (event) => {
      this.props.onAction(event);

      if (this.props.stopPropagation) {
        return true;
      }
      return false;
    },
    QUICK_ACTION_TOGGLE: (event) => {
      event.preventDefault();

      this.props.onClick();

      if (this.props.stopPropagation) {
        return true;
      }
      return false;
    },
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return [
      <Shortcut
        key="QUICK_ACTION_POS"
        name="QUICK_ACTION_POS"
        handler={this.handlers.QUICK_ACTION_POS}
      />,
      <Shortcut
        key="QUICK_ACTION_TOGGLE"
        name="QUICK_ACTION_TOGGLE"
        handler={this.handlers.QUICK_ACTION_TOGGLE}
      />,
    ];
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} handleClick
 * @prop {func} onClick
 * @prop {bool} [stopPropagation]
 */
QuickActionsContextShortcuts.propTypes = {
  onAction: PropTypes.func.isRequired,
  onClick: PropTypes.func.isRequired,
  stopPropagation: PropTypes.bool,
};
