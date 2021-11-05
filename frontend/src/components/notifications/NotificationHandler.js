import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import Notification from './Notification';

const EMPTY_OBJECT = { notifications: {} };

class NotificationHandler extends PureComponent {
  render() {
    const { notifications, children } = this.props;

    return (
      <div>
        <div className="notification-handler">
          {Object.keys(notifications).map((key) => (
            <Notification key={key} item={notifications[key]} />
          ))}
        </div>
        <div className="root-children">{children}</div>
      </div>
    );
  }
}

NotificationHandler.propTypes = {
  notifications: PropTypes.object.isRequired,
  children: PropTypes.element,
};

function mapStateToProps(state) {
  const { appHandler } = state;
  const { notifications } = appHandler || EMPTY_OBJECT;

  return {
    notifications,
  };
}

export default connect(mapStateToProps)(NotificationHandler);
