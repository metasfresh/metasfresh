import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';
import { arePropTypesIdentical } from '../../utils';

export default class TableContextShortcuts extends Component {
  handlers = {
    TOGGLE_EXPAND: event => {
      event.preventDefault();

      this.props.onToggleExpand();
    },
    TOGGLE_QUICK_INPUT: event => {
      event.preventDefault();

      this.props.onToggleQuickInput();
    },
  };

  shouldComponentUpdate = nextProps =>
    !arePropTypesIdentical(nextProps, this.props);

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
