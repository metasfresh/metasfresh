import counterpart from 'counterpart';
import Moment from 'moment';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module InboxItem
 * @extends Component
 */
class InboxItem extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * @method renderIconFromTarget
   * @summary ToDo: Describe the method
   * @param {*} target
   * @todo Write the documentation
   */
  renderIconFromTarget = (target) => {
    switch (target) {
      case '143':
        return 'sales';
      default:
        return 'system';
    }
  };

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount() {
    document.getElementsByClassName('js-inbox-wrapper')[0].focus();
  }

  /**
   * @method renderCancelButton
   * @summary ToDo: Describe the method
   * @param {event} event
   * @todo Write the documentation
   */
  handleKeyDown = (e) => {
    const { close } = this.props;
    switch (e.key) {
      case 'ArrowDown':
        e.preventDefault();
        if (document.activeElement.nextSibling) {
          document.activeElement.nextSibling.focus();
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

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { item, onClick, onDelete } = this.props;
    return (
      <div
        onClick={onClick}
        onKeyDown={this.handleKeyDown}
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
              this.renderIconFromTarget(item.target && item.target.documentType)
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
              <a
                href="javascript:void(0)"
                className="inbox-item-delete"
                onClick={onDelete}
              >
                {counterpart.translate('window.Delete.caption')}
              </a>

              <span>Notification</span>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} [close]
 * @prop {*} [item]
 * @prop {*} [onClick]
 * @prop {*} [onDelete]
 * @todo Check props. Which proptype? Required or optional?
 */
InboxItem.propTypes = {
  close: PropTypes.any,
  item: PropTypes.any,
  onClick: PropTypes.any,
  onDelete: PropTypes.any,
};

export default InboxItem;
