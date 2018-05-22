import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';
import { arePropTypesIdentical } from '../../utils';

export default class DocumentStatusContextShortcuts extends Component {
  handleShortcut = event => {
    const { onDocumentCompleteStatus } = this.props;

    event.preventDefault();

    return onDocumentCompleteStatus();
  };

  shouldComponentUpdate = nextProps =>
    !arePropTypesIdentical(nextProps, this.props);

  render() {
    return <Shortcut name="COMPLETE_STATUS" handler={this.handleShortcut} />;
  }
}
