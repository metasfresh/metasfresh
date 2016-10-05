import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Notification from '../app/Notification';

import {
    notificationState
} from '../../actions/AppActions';

class NotificationHandler extends Component {
    constructor(props) {
        super(props);

        this.state = {
            notification: 'false'
        }
    }

    componentWillReceiveProps(props) {
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
    notification: PropTypes.string.isRequired
};

function mapStateToProps(state) {
    const {appHandler} = state;
    const {
        notification
    } = appHandler || {
        notification: 'false'
    }

    return {
      notification
    }
}

NotificationHandler = connect(mapStateToProps)(NotificationHandler)

export default NotificationHandler
