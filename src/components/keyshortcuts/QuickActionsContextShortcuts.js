import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Shortcut } from '../keyshortcuts';

export default class QuickActionsContextShortcuts extends Component {
  handlers = {
    QUICK_ACTION_POS: event => {
      event.preventDefault();

      this.props.handleClick();

      if (this.props.stopPropagation) {
        return true;
      }
      return false;
    },
    QUICK_ACTION_TOGGLE: event => {
      event.preventDefault();

      this.props.onClick();

      if (this.props.stopPropagation) {
        return true;
      }
      return false;
    },
  };

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

QuickActionsContextShortcuts.propTypes = {
  handleClick: PropTypes.func.isRequired,
  onClick: PropTypes.func.isRequired,
  stopPropagation: PropTypes.bool,
};
