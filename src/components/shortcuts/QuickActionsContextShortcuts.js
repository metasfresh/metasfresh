import React, { Component } from 'react';

import { Shortcut } from '../Shortcuts';

export default class QuickActionsContextShortcuts extends Component {
  handlers = {
    QUICK_ACTION_POS: event => {
      event.preventDefault();

      this.props.handleClick();
    },
    QUICK_ACTION_TOGGLE: event => {
      event.preventDefault();

      this.props.onClick();
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
