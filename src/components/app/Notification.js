import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

class Notification extends Component {
    constructor(props) {
        super(props);
    }

    componentWillReceiveProps(props) {

    }

    rendernotification = () => {
      return (
      <div className="notification-item">
          <div className="notification-header">Error</div>
          <div className="notification-content">Lorem ipsum dolor sit amet, lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet.</div>
      </div>
      )
    }


    render() {
        return (
          <div>
            {this.props.notification == 'true' ? this.rendernotification() : ''}
          </div>
        )
    }
}

Notification.propTypes = {
    dispatch: PropTypes.func.isRequired
};


Notification = connect()(Notification)

export default Notification