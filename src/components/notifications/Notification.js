import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { deleteNotification } from '../../actions/AppActions';

class Notification extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isClosing: false,
      isDisplayedMore: false,
    };

    this.closing = null;
  }

  componentDidMount() {
    const { item } = this.props;

    if (item.time > 0) {
      this.setClosing();
    }

    const th = this;
    setTimeout(() => {
      th.setState({
        isClosing: true,
      });
    }, 10);
  }

  componentWillUpdate(nextProps) {
    const { item } = this.props;

    if (item.count !== nextProps.item.count) {
      this.handleClosing(false);

      const th = this;

      setTimeout(() => {
        th.handleClosing(true);
      }, 10);
    }
  }

  setClosing = () => {
    const { dispatch, item } = this.props;

    if (item.time === 0) {
      this.closing = null;
    } else {
      this.closing = setTimeout(() => {
        dispatch(deleteNotification(item.title));
      }, item.time);
    }
  };

  handleCloseButton = () => {
    const { dispatch, item } = this.props;

    this.closing && clearInterval(this.closing);

    dispatch(deleteNotification(item.title));
  };

  handleClosing = shouldClose => {
    if (this.props.item && this.props.item.time !== 0) {
      shouldClose ? this.setClosing() : clearInterval(this.closing);

      this.setState({
        isClosing: shouldClose,
      });
    }
  };

  handleToggleMore = () => {
    this.setState({
      isDisplayedMore: true,
    });
  };

  render() {
    const { item } = this.props;
    const { isClosing, isDisplayedMore } = this.state;
    let progress = item.progress;

    return (
      <div
        className={
          'notification-item ' +
          (item.notifType ? item.notifType + ' ' : 'error ')
        }
        onMouseEnter={() => this.handleClosing(false)}
        onMouseLeave={() => this.handleClosing(true)}
      >
        <div className="notification-header">
          {item.title}{' '}
          {item.count ? (
            <span
              className={
                'tag tag-sm tag-default ' +
                ('tag-' + (item.notifType ? item.notifType : 'error '))
              }
            >
              {item.count}
            </span>
          ) : (
            ''
          )}
          <i
            onClick={() => this.handleCloseButton()}
            className="meta-icon-close-1"
          />
        </div>
        <div className="notification-content">
          {item.shortMsg ? item.shortMsg + ' ' : item.msg}
          {item.shortMsg &&
            item.msg &&
            !isDisplayedMore && (
              <u
                className="text-xs-right text-small pointer"
                onClick={this.handleToggleMore}
              >
                (read more)
              </u>
            )}
          {isDisplayedMore ? <p>{item.msg}</p> : ''}
        </div>
        <div
          className={
            'progress-bar ' + (item.notifType ? item.notifType : 'error')
          }
          style={
            typeof progress === 'number'
              ? { width: `${progress}%`, transition: 'width 0s' }
              : isClosing
                ? { width: 0, transition: 'width 5s linear' }
                : { width: '100%', transition: 'width 0s' }
          }
        />
      </div>
    );
  }
}

Notification.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect()(Notification);
