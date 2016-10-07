import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    deleteNotification
} from '../../actions/AppActions';

class Notification extends Component {
    constructor(props) {
        super(props);

    }

    renderNotification = (item,index) => {
      const {dispatch} = this.props;

      if(item.time > 0) {
        setTimeout(function(){
          dispatch(deleteNotification(item));
        }, item.time);
      }
      


      return (
        <div className={"notification-item " + (item.notifType ? item.notifType : 'error')} key={index}>
          <div className="notification-header"> {item.title} <i onClick={() => this.closeNotification(item)} className="meta-icon-close-1"></i></div>
          <div className="notification-content"> {item.msg} </div>
        </div> 
      )
    }

    closeNotification = (item) => {
      const {dispatch} = this.props;
      dispatch(deleteNotification(item));
    }


    render() {
        const {notification} = this.props;
        return (
          <div>
            
            {notification.notifications && notification.notifications.map((item,index) =>
              this.renderNotification(item,index)
            )}

          </div>
        )
    }
}

Notification.propTypes = {
    dispatch: PropTypes.func.isRequired
};


Notification = connect()(Notification)

export default Notification