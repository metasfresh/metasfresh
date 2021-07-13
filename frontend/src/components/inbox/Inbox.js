import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { get } from 'lodash';

import {
  deleteUserNotification,
  markAllAsRead,
  markAsRead,
} from '../../actions/AppActions';
import InboxItem from './InboxItem';

/**
 * @file Class based component.
 * @module Inbox
 * @extends Component
 */
class Inbox extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * @method handleClick
   * @summary ToDo: Describe the method
   * @param {*} item
   * @todo Write the documentation
   */
  handleClick = (item) => {
    const { dispatch, close, location } = this.props;
    if (item.target) {
      switch (item.target.targetType) {
        case 'window':
          dispatch(
            push(`/window/${item.target.windowId}/${item.target.documentId}`)
          );
          break;
        case 'view': {
          // in case we're on the same page, we want to add a hash param
          // to force refresh of DocumentList component so in order to do that
          // we check if viewId's are equal
          let samePageParam = '';
          const locationViewId = get(location, 'query.viewId')
            ? get(location, 'query.viewId').split('-')[0]
            : null;
          const targetViewId = item.target.viewId
            ? item.target.viewId.split('-')[0]
            : null;

          if (locationViewId === targetViewId) {
            samePageParam = '#notification';
          }

          dispatch(
            push(
              `/window/${item.target.windowId}/?viewId=${item.target.viewId}${samePageParam}`
            )
          );

          break;
        }
      }
    }

    if (!item.read) {
      markAsRead(item.id);
    }
    close && close();
  };

  /**
   * @method handleMarkAllAsRead
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleMarkAllAsRead = () => {
    const { close } = this.props;

    markAllAsRead();

    close && close();
  };

  /**
   * @method handleShowAll
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleShowAll = () => {
    const { close, dispatch } = this.props;
    dispatch(push('/inbox'));
    close && close();
  };

  /**
   * @method handleDelete
   * @summary ToDo: Describe the method
   * @param {object} event
   * @param {*} item
   * @todo Write the documentation
   */
  handleDelete = (e, item) => {
    e.preventDefault();
    e.stopPropagation();

    deleteUserNotification(item.id).then(() => {});
  };

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method
   * @param {object} event
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    const { close } = this.props;
    const inboxItem = document.getElementsByClassName('js-inbox-item')[0];
    switch (e.key) {
      case 'ArrowDown':
        e.preventDefault();
        if (document.activeElement.classList.contains('js-inbox-wrapper')) {
          if (inboxItem) {
            inboxItem.focus();
          }
        }
        break;

      case 'Tab':
        close && close();
        break;

      case 'Escape':
        e.preventDefault();
        close && close();
        break;
    }
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { open, inbox, all, close } = this.props;

    return (
      <div
        className="js-inbox-wrapper js-not-unselect"
        onKeyDown={(e) => this.handleKeyDown(e)}
      >
        {(all || open) && (
          <div className={all ? 'inbox-all ' : 'inbox'}>
            <div className={'inbox-body ' + (!all ? 'breadcrumbs-shadow' : '')}>
              <div className="inbox-header">
                <span className="inbox-header-title">
                  {counterpart.translate('window.inbox.caption')}
                </span>
                <span onClick={this.handleMarkAllAsRead} className="inbox-link">
                  {counterpart.translate('window.markAsRead.caption')}
                </span>
              </div>
              <div className={!all ? 'inbox-list' : ''}>
                {inbox &&
                  inbox.notifications.map((item, id) => (
                    <InboxItem
                      key={id}
                      item={item}
                      close={close}
                      onClick={() => this.handleClick(item)}
                      onDelete={(e) => this.handleDelete(e, item)}
                    />
                  ))}
                {inbox && inbox.notifications.length == 0 && (
                  <div className="inbox-item inbox-item-empty">
                    {counterpart.translate('window.inbox.empty')}
                  </div>
                )}
              </div>
              <div className="inbox-footer">
                {!all && (
                  <div
                    onClick={this.handleShowAll}
                    className="inbox-link text-center"
                  >
                    {counterpart.translate('window.allInbox.caption')} &gt;&gt;
                  </div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }
}

/**
 * @method addClickOutsideHandler
 * @summary ToDo: Describe the method
 * @param {*} Child
 * @todo Write the documentation
 */
const addClickOutsideHandler = (Child) => {
  return class WithClickOutsideHandler extends Component {
    static propTypes = {
      close: PropTypes.func,
    };

    handleClickOutside = () => {
      const { close } = this.props;
      close && close();
    };

    render() {
      return <Child {...this.props} />;
    }
  };
};

/**
 * @typedef {object} Props Component props
 * @prop {func} dispatch
 * @prop {bool} [open]
 * @prop {bool} modalVisible
 * @prop {object} [location]
 * @prop {func} [close]
 * @prop {shape} [inbox]
 * @prop {bool} [all]
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
Inbox.propTypes = {
  dispatch: PropTypes.func.isRequired,
  open: PropTypes.bool,
  modalVisible: PropTypes.bool.isRequired,
  location: PropTypes.object,
  close: PropTypes.func,
  inbox: PropTypes.object,
  all: PropTypes.bool,
};

Inbox.defaultProps = {};

const routerInbox = withRouter(
  connect((state, props) => ({
    modalVisible: state.windowHandler.modal.visible,
    location: props.router.location,
  }))(Inbox)
);

export default onClickOutside(addClickOutsideHandler(routerInbox));
