import React, { Component } from 'react';

import { Shortcut } from '../Shortcuts';

export default class TableContextShortcuts extends Component {
  handlers = {
    TOGGLE_EXPAND: event => {
      event.preventDefault();

      this.props.handleToggleExpand();
    },
    TOGGLE_QUICK_INPUT: event => {
      event.preventDefault();

      this.props.handleToggleQuickInput();
    },
  };

  render() {
    return [
      <Shortcut
        key="TOGGLE_EXPAND"
        name="TOGGLE_EXPAND"
        handler={this.handlers.TOGGLE_EXPAND}
      />,
      <Shortcut
        key="TOGGLE_QUICK_INPUT"
        name="TOGGLE_QUICK_INPUT"
        handler={this.handlers.TOGGLE_QUICK_INPUT}
      />,
    ];
  }
}
