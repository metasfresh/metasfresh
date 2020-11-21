import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { SHOW_READ_MORE_FROM } from '../../constants/Constants';
import { deleteNotification } from '../../actions/AppActions';
class Notification extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isClosing: false,
      isDisplayedMore: false,
    };

    this.closing = null;
    this.allowMouse = props.item.shortMsg === 'disableMouse' ? false : true;
  }

  componentDidMount() {
    const { item } = this.props;

    if (item.time > 0) {
      this.setClosing();
    }

    setTimeout(() => {
      this.setState({
        isClosing: true,
      });
    }, 10);
  }

  UNSAFE_componentWillUpdate(nextProps) {
    const { item } = this.props;

    if (item.count !== nextProps.item.count) {
      this.handleClosing(false);

      setTimeout(() => {
        this.handleClosing(true);
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

    if (item.onCancel) {
      item.onCancel.cancel('Operation canceled by the user.');
    } else {
      this.closing && clearInterval(this.closing);
    }

    dispatch(deleteNotification(item.title));
  };

  handleClosing = (shouldClose) => {
    const { item } = this.props;

    // This is to prevent auto-closing when notification pops-up
    // under user's mouse cursor
    if (!this.allowMouse) {
      this.allowMouse = true;

      return;
    }

    if (item && item.time !== 0) {
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
    const { notifType, msg } = item;
    let { shortMsg } = item;
    let progress = item.progress;

    if (shortMsg === 'disableMouse') {
      shortMsg = null;
    }

    return (
      <div
        className={classnames(
          'notification-item',
          { [`${notifType}`]: notifType },
          { error: !notifType }
        )}
        onMouseEnter={() => this.handleClosing(false)}
        onMouseLeave={() => this.handleClosing(true)}
      >
        <div className="notification-content">
          {shortMsg && msg.length > SHOW_READ_MORE_FROM ? shortMsg + ' ' : msg}
          {msg.length > SHOW_READ_MORE_FROM &&
            shortMsg &&
            msg &&
            !isDisplayedMore && (
              <u
                className="text-right text-small pointer"
                onClick={this.handleToggleMore}
              >
                (read more)
              </u>
            )}
          {isDisplayedMore ? <p>{msg}</p> : ''}
        </div>
        <div
          className={classnames(
            'progress-bar',
            { [`${notifType}`]: notifType },
            { error: !notifType }
          )}
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
  item: PropTypes.object,
};

export default connect()(Notification);
