import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';
import { arePropTypesIdentical } from '../../utils';

export default class DocumentStatusContextShortcuts extends Component {
  handleShortcut = event => {
    const { handleDocumentCompleteStatus } = this.props;

    event.preventDefault();

    return handleDocumentCompleteStatus();
  };

  shouldComponentUpdate = nextProps =>
    !arePropTypesIdentical(nextProps, this.props);

  render() {
    return <Shortcut name="COMPLETE_STATUS" handler={this.handleShortcut} />;
  }
}
