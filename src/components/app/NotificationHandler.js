import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Notification from '../app/Notification';

import {
    addNotification
} from '../../actions/AppActions';

class NotificationHandler extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const {notification} = this.props;

        return (
          <div className="notification-handler">

          {notification.notifications && notification.notifications.map((item,index) =>
              <Notification
                key={index}
                index={index}
                item={item}
              />
          )}

          </div>
        )
    }
}

NotificationHandler.propTypes = {
    notification: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {appHandler} = state;
    const {
        notification
    } = appHandler || {
        notification: {}
    }

    return {
      notification
    }
}

NotificationHandler = connect(mapStateToProps)(NotificationHandler)

export default NotificationHandler
