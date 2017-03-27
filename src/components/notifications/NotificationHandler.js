import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Notification from './Notification';

class NotificationHandler extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {notifications, dontShowNotifications} = this.props;
        return (
            <div className="notification-handler">
                {Object.keys(notifications).map((key) => {
                    console.warn(notifications[key].msg);
                    return !dontShowNotifications &&
                    <Notification
                        key={key}
                        item={notifications[key]}
                    />
                    }
                )}
            </div>
        )
    }
}

NotificationHandler.propTypes = {
    notifications: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {appHandler} = state;
    const {
        notifications
    } = appHandler || {
        notifications: {}
    }

    return {
        notifications
    }
}

NotificationHandler = connect(mapStateToProps)(NotificationHandler)

export default NotificationHandler
