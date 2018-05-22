import React, { Component } from 'react';

import { Shortcut } from '../keyshortcuts';

export default class PaginationContextShortcuts extends Component {
  handlers = {
    FIRST_PAGE: event => {
      event.preventDefault();

      return this.props.onFirstPage();
    },
    LAST_PAGE: event => {
      event.preventDefault();

      return this.props.onLastPage();
    },
    NEXT_PAGE: event => {
      event.preventDefault();

      if (this.props.pages > 1) return this.props.onNextPage();
      return;
    },
    PREV_PAGE: event => {
      event.preventDefault();

      if (this.props.pages > 1) return this.props.onPrevPage();
      return;
    },
    SELECT_ALL_ROWS: event => {
      event.preventDefault();

      return this.props.onSelectAll();
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
