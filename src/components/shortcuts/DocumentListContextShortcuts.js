import React, { Component } from 'react';

import { Shortcut } from '../shortcuts';
import { arePropTypesIdentical } from '../../utils';

export default class DocumentListContextShortcuts extends Component {
  handlers = {
    OPEN_SELECTED: event => {
      event.preventDefault();

      if (this.props.handleOpenNewTab) {
        this.props.handleOpenNewTab();
      }
    },
    REMOVE_SELECTED: event => {
      event.preventDefault();

      if (this.props.handleDelete) {
        this.props.handleDelete();
      }
    },
    ADVANCED_EDIT: event => {
      event.preventDefault();

      if (this.props.handleAdvancedEdit) {
        this.props.handleAdvancedEdit();
      }
    },
    SELECT_ALL_LEAFS: event => {
      event.preventDefault();

      if (this.props.getAllLeafs) {
        this.props.getAllLeafs();
      }
    },
    EXPAND_INDENT: event => {
      event.preventDefault();

      if (this.props.handleIndent) {
        this.props.handleIndent(true);
      }
    },
    COLLAPSE_INDENT: event => {
      event.preventDefault();

      if (this.props.handleIndent) {
        this.props.handleIndent(false);
      }
    },
  };

  shouldComponentUpdate = nextProps =>
    !arePropTypesIdentical(nextProps, this.props);

  render() {
    return [
      <Shortcut
        key="OPEN_SELECTED"
        name="OPEN_SELECTED"
        handler={this.handlers.OPEN_SELECTED}
      />,
      <Shortcut
        key="REMOVE_SELECTED"
        name="REMOVE_SELECTED"
        handler={this.handlers.REMOVE_SELECTED}
      />,
      <Shortcut
        key="ADVANCED_EDIT"
        name="ADVANCED_EDIT"
        handler={this.handlers.ADVANCED_EDIT}
      />,
      <Shortcut
        key="SELECT_ALL_LEAFS"
        name="SELECT_ALL_LEAFS"
        handler={this.handlers.SELECT_ALL_LEAFS}
      />,
      <Shortcut
        key="EXPAND_INDENT"
        name="EXPAND_INDENT"
        handler={this.handlers.EXPAND_INDENT}
      />,
      <Shortcut
        key="COLLAPSE_INDENT"
        name="COLLAPSE_INDENT"
        handler={this.handlers.COLLAPSE_INDENT}
      />,
    ];
  }
}
