import React, { Component } from 'react';

import { Shortcut } from '../Shortcuts';

export default class DocumentStatusContextShortcuts extends Component {
  handleShortcut = event => {
    const { handleDocumentCompleteStatus } = this.props;

    event.preventDefault();

    return handleDocumentCompleteStatus();
  };

  render() {
    return <Shortcut name="COMPLETE_STATUS" handler={this.handleShortcut} />;
  }
}
