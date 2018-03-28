import React, { Component } from 'react';

import { Shortcut } from '../shortcuts';

export default class ModalContextShortcuts extends Component {
  handlers = {
    APPLY: event => {
      event.preventDefault();

      this.blurActiveElement();

      this.props.apply && this.props.apply();
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
      <Shortcut key="APPLY" name="APPLY" handler={this.handlers.APPLY} />,
      <Shortcut key="CANCEL" name="CANCEL" handler={this.handlers.CANCEL} />,
    ];
  }
}
