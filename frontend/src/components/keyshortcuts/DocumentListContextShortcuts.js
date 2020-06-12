import React, { Fragment, PureComponent } from 'react';
import PropTypes from 'prop-types';
import { Shortcut } from '../keyshortcuts';

export default class DocumentListContextShortcuts extends PureComponent {
  handlers = {
    OPEN_SELECTED: (event) => {
      const { onOpenNewTab } = this.props;

      event.preventDefault();

      if (onOpenNewTab) {
        this.handleOpenNewTab();
      }
    },
    REMOVE_SELECTED: (event) => {
      const { onDelete } = this.props;

      event.preventDefault();

      if (onDelete) {
        onDelete();
      }
    },
    ADVANCED_EDIT: (event) => {
      const { onAdvancedEdit } = this.props;

      event.preventDefault();

      if (onAdvancedEdit) {
        onAdvancedEdit();

        return true;
      }

      return false;
    },
    SELECT_ALL_LEAVES: (event) => {
      const { onGetAllLeaves } = this.props;

      event.preventDefault();

      if (onGetAllLeaves) {
        onGetAllLeaves();
      }
    },
    EXPAND_INDENT: (event) => {
      const { onIndent } = this.props;

      event.preventDefault();

      if (onIndent) {
        onIndent(true);
      }
    },
    COLLAPSE_INDENT: (event) => {
      const { onIndent, commentsOpened } = this.props;

      if (commentsOpened) return false;
      event.preventDefault();

      if (onIndent) {
        onIndent(false);
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
          key="SELECT_ALL_LEAVES"
          name="SELECT_ALL_LEAVES"
          handler={this.handlers.SELECT_ALL_LEAVES}
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
  onGetAllLeaves: PropTypes.any,
  onIndent: PropTypes.func,
  commentsOpened: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.string,
  selected: PropTypes.array,
};
