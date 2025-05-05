import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {
  deleteUserNotification,
  markAllAsRead,
  markAsRead,
} from '../../actions/AppActions';
import InboxItem from './InboxItem';
import { extractWindowIdFromViewId } from '../../utils/windowHelpers';

class Inbox extends Component {
  isCurrentWindowId = (windowId) => {
    const { location } = this.props;
    const currentViewId = get(location, 'query.viewId');
    const currentWindowId = extractWindowIdFromViewId(currentViewId);
    return currentWindowId && currentWindowId === windowId;
  };

  handleClick = (item) => {
    const { close } = this.props;

    if (item.target) {
      this.handleItemTarget(item.target);
    }

    if (!item.read) {
      markAsRead(item.id);
    }

    close && close();
  };

  handleItemTarget = (itemTarget) => {
    const { history } = this.props;
    switch (itemTarget.targetType) {
      case 'window':
        history.push(`/window/${itemTarget.windowId}/${itemTarget.documentId}`);
        break;
      case 'view': {
        // keep in sync with de.metas.notification.UserNotificationRequest.TargetViewAction

        const targetViewId = itemTarget.viewId;
        const targetWindowId = itemTarget.windowId;

        let targetLocation;
        if (targetViewId === 'DEFAULT') {
          targetLocation = `/window/${targetWindowId}`;
        } else {
          targetLocation = `/window/${itemTarget.windowId}/?viewId=${targetViewId}`;
        }

        if (this.isCurrentWindowId(targetWindowId)) {
          // In case we're on the same page,
          // for some reason DocumentList won't refresh on history.push,
          // so we force a full page reload by setting `window.location`.
          window.location = targetLocation;
        } else {
          history.push(targetLocation);
        }

        break;
      }
    }
  };

  handleMarkAllAsRead = () => {
    const { close } = this.props;

    markAllAsRead();

    close && close();
  };

  handleShowAll = () => {
    const { close, history } = this.props;
    history.push('/inbox');
    close && close();
  };

  handleDelete = (e, item) => {
    e.preventDefault();
    e.stopPropagation();

    deleteUserNotification(item.id).then(() => {});
  };

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

  render() {
    const { open, inbox, all, close } = this.props;

    return (
      <div
        className="js-inbox-wrapper js-not-unselect"
        onKeyDown={this.handleKeyDown}
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
                {inbox && inbox.notifications.length === 0 && (
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
 * @prop {bool} [open]
 * @prop {bool} modalVisible
 * @prop {object} [location]
 * @prop {func} [close]
 * @prop {shape} [inbox]
 * @prop {bool} [all]
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
Inbox.propTypes = {
  open: PropTypes.bool,
  modalVisible: PropTypes.bool.isRequired,
  location: PropTypes.object,
  close: PropTypes.func,
  inbox: PropTypes.object,
  all: PropTypes.bool,
  history: PropTypes.object,
};

Inbox.defaultProps = {};

const routerInbox = withRouter(
  connect((state, { location }) => ({
    modalVisible: state.windowHandler.modal.visible,
    location,
  }))(Inbox)
);

export default onClickOutside(addClickOutsideHandler(routerInbox));
