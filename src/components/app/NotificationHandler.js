import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Notification from '../app/Notification';

import {
    addNotification
} from '../../actions/AppActions';

class NotificationHandler extends Component {
    constructor(props) {
        super(props);

        this.state = {
            notification: {
              visible: false,
              notifications: [],
              title: '',
              msg: '',
              time: 0
            }
        }
    }

    render() {
        return (
          <div className="notification-handler"> 

          <Notification notification={this.props.notification} />

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
