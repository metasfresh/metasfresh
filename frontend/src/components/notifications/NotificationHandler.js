import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import Notification from './Notification';

class NotificationHandler extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { notifications, children } = this.props;

    return (
      <div>
        <div className="notification-handler">
          {Object.keys(notifications).map(key => (
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
};

function mapStateToProps(state) {
  const { appHandler } = state;
  const { notifications } = appHandler || {
    notifications: {},
  };

  return {
    notifications,
  };
}

export default connect(mapStateToProps)(NotificationHandler);
