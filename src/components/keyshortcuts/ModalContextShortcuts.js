import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';

export default class ModalContextShortcuts extends Component {
  handlers = {
    APPLY: event => {
      event.preventDefault();

      const { visibleFilter, apply } = this.props;

      this.blurActiveElement();
      apply && apply();

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
      <Shortcut key="APPLY" name="APPLY" handler={this.handlers.APPLY} />,
      <Shortcut key="CANCEL" name="CANCEL" handler={this.handlers.CANCEL} />,
    ];
  }
}
