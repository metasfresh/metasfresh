import React, { Fragment, PureComponent } from 'react';
import PropTypes from 'prop-types';
import { Shortcut } from '../keyshortcuts';

export default class DocumentListContextShortcuts extends PureComponent {
  handlers = {
    OPEN_SELECTED: (event) => {
      event.preventDefault();

      if (this.props.onOpenNewTab) {
        this.handleOpenNewTab();
      }
    },
    REMOVE_SELECTED: (event) => {
      event.preventDefault();

      if (this.props.onDelete) {
        this.props.onDelete();
      }
    },
    ADVANCED_EDIT: (event) => {
      event.preventDefault();

      if (this.props.onAdvancedEdit) {
        this.onAdvancedEdit();

        return true;
      }

      return false;
    },
    SELECT_ALL_LEAFS: (event) => {
      event.preventDefault();

      if (this.props.onGetAllLeafs) {
        this.props.onGetAllLeafs();
      }
    },
    EXPAND_INDENT: (event) => {
      event.preventDefault();

      if (this.props.onIndent) {
        this.props.onIndent(true);
      }
    },
    COLLAPSE_INDENT: (event) => {
      if (this.props.commentsOpened) return false;
      event.preventDefault();

      if (this.props.onIndent) {
        this.props.onIndent(false);
      }
    },
  };

  handleOpenNewTab = () => {
    const { selected, windowId, onOpenNewTab } = this.props;
    onOpenNewTab(selected, windowId);
  };

  handleAdvancedEdit = () => {
    const { windowId, tabId, selected, onAdvancedEdit } = this.props;

    onAdvancedEdit(windowId, tabId, selected);
  };

  render() {
    return (
      <Fragment>
        <Shortcut
          key="OPEN_SELECTED"
          name="OPEN_SELECTED"
          handler={this.handlers.OPEN_SELECTED}
        />
        <Shortcut
          key="REMOVE_SELECTED"
          name="REMOVE_SELECTED"
          handler={this.handlers.REMOVE_SELECTED}
        />
        <Shortcut
          key="ADVANCED_EDIT"
          name="ADVANCED_EDIT"
          handler={this.handlers.ADVANCED_EDIT}
        />
        <Shortcut
          key="SELECT_ALL_LEAFS"
          name="SELECT_ALL_LEAFS"
          handler={this.handlers.SELECT_ALL_LEAFS}
        />
        <Shortcut
          key="EXPAND_INDENT"
          name="EXPAND_INDENT"
          handler={this.handlers.EXPAND_INDENT}
        />
        <Shortcut
          key="COLLAPSE_INDENT"
          name="COLLAPSE_INDENT"
          handler={this.handlers.COLLAPSE_INDENT}
        />
      </Fragment>
    );
  }
}

DocumentListContextShortcuts.propTypes = {
  onOpenNewTab: PropTypes.func,
  onDelete: PropTypes.func,
  onAdvancedEdit: PropTypes.func,
  onGetAllLeafs: PropTypes.any,
  onIndent: PropTypes.func,
  commentsOpened: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.string,
  selected: PropTypes.array,
};
