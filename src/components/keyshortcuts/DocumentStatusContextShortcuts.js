import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Shortcut } from '../keyshortcuts';
import { arePropTypesIdentical } from '../../utils';

export default class DocumentStatusContextShortcuts extends Component {
  handleShortcut = (event) => {
    const { handleDocumentCompleteStatus } = this.props;

    event.preventDefault();

    return handleDocumentCompleteStatus();
  };

  shouldComponentUpdate = (nextProps) =>
    !arePropTypesIdentical(nextProps, this.props);

  render() {
    return <Shortcut name="COMPLETE_STATUS" handler={this.handleShortcut} />;
  }
}

DocumentStatusContextShortcuts.propTypes = {
  handleDocumentCompleteStatus: PropTypes.func,
};
