import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';

export default class PaginationContextShortcuts extends Component {
  handlers = {
    FIRST_PAGE: event => {
      event.preventDefault();

      const activeElement = document.activeElement;

      // if input is selected global shortcuts should handle this
      if (activeElement && activeElement.nodeName === 'INPUT') {
        return false;
      }

      return this.props.handleFirstPage();
    },
    LAST_PAGE: event => {
      event.preventDefault();

      const activeElement = document.activeElement;

      if (activeElement && activeElement.nodeName === 'INPUT') {
        return false;
      }

      return this.props.handleLastPage();
    },
    NEXT_PAGE: event => {
      event.preventDefault();

      if (this.props.pages > 1) return this.props.handleNextPage();
      return;
    },
    PREV_PAGE: event => {
      event.preventDefault();

      if (this.props.pages > 1) return this.props.handlePrevPage();
      return;
    },
    SELECT_ALL_ROWS: event => {
      event.preventDefault();

      return this.props.handleSelectAll();
    },
  };

  render() {
    return [
      <Shortcut
        key="FIRST_PAGE"
        name="FIRST_PAGE"
        handler={this.handlers.FIRST_PAGE}
      />,
      <Shortcut
        key="LAST_PAGE"
        name="LAST_PAGE"
        handler={this.handlers.LAST_PAGE}
      />,
      <Shortcut
        key="NEXT_PAGE"
        name="NEXT_PAGE"
        handler={this.handlers.NEXT_PAGE}
      />,
      <Shortcut
        key="PREV_PAGE"
        name="PREV_PAGE"
        handler={this.handlers.PREV_PAGE}
      />,
      <Shortcut
        key="SELECT_ALL_ROWS"
        name="SELECT_ALL_ROWS"
        handler={this.handlers.SELECT_ALL_ROWS}
      />,
    ];
  }
}
