import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    deleteNotification
} from '../../actions/AppActions';

class Notification extends Component {
    constructor(props) {
        super(props);

    }

    rendernotification = (item,index) => {
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

    set_time_out = ( id, code, time ) => {
        if( id in timeout_handles ) {
            clearTimeout( timeout_handles[id] )
        }

        timeout_handles[id] = setTimeout( code, time )
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
              this.rendernotification(item,index)
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