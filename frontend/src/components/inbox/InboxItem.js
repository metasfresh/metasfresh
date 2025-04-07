import counterpart from 'counterpart';
import Moment from 'moment';
import React from 'react';
import PropTypes from 'prop-types';

const InboxItem = ({ item, onClick, onDelete }) => {
  return (
    <div
      onClick={onClick}
      onKeyDown={handleKeyDown}
      tabIndex={0}
      className={
        'inbox-item js-inbox-item pointer ' +
        (!item.read ? 'inbox-item-unread ' : '')
      }
    >
      {item.important && (
        <div className="inbox-item-icon inbox-item-icon-sm">
          <i className="meta-icon-important" />
        </div>
      )}
      <div className="inbox-item-icon">
        <i
          className={
            'meta-icon-' +
            renderIconFromTarget(item.target && item.target.documentType)
          }
        />
      </div>
      <div className="inbox-item-content">
        <div className="inbox-item-title">{item.message}</div>
        <div className="inbox-item-footer">
          <div title={Moment(item.timestamp).format('DD.MM.YYYY HH:mm:ss')}>
            {Moment(item.timestamp).fromNow()}
          </div>
          <div>
            <button className="inbox-item-delete" onClick={onDelete}>
              {counterpart.translate('window.Delete.caption')}
            </button>
            <span>Notification</span>
          </div>
        </div>
      </div>
    </div>
  );
};

InboxItem.propTypes = {
  close: PropTypes.func,
  item: PropTypes.object,
  onClick: PropTypes.func,
  onDelete: PropTypes.func,
};

export default InboxItem;

//
//
//

const renderIconFromTarget = (target) => {
  switch (target) {
    case '143':
      return 'sales';
    default:
      return 'system';
  }
};

//
//
//

const handleKeyDown = (e) => {
  switch (e.key) {
    case 'ArrowDown':
      e.preventDefault();
      if (document.activeElement.nextSibling) {
        document.activeElement.nextSibling?.focus?.();
      }
      break;
    case 'ArrowUp':
      e.preventDefault();
      if (document.activeElement.previousSibling) {
        document.activeElement.previousSibling.focus();
      }
      break;
    case 'Enter':
      e.preventDefault();
      document.activeElement.click();
      break;
    case 'Escape':
      e.preventDefault();
      close && close();
      break;
  }
};
