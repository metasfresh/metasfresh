import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    deleteNotification
} from '../../actions/AppActions';

class Notification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            notificationMounted: false
        }
    }

    renderNotification = (item, index) => {
        const {notificationMounted} = this.state;

        return (
            <div className={'notification-item ' + (item.notifType ? item.notifType : 'error') + (notificationMounted ? ' notif-animate' : '')}>
                <div className="notification-header"> {item.title} <i onClick={() => this.closeNotification(index)} className="meta-icon-close-1"></i></div>
                <div className="notification-content"> {item.msg} </div>
            </div>
        )
    }

    closeNotification = (id) => {
        const {dispatch} = this.props;

        this.setState({
            notificationMounted: false
        })

        setTimeout(function(){
            dispatch(deleteNotification(id));
        }, 300);
    }

    componentDidMount() {
        const {item, index, dispatch} = this.props;

        if(item.time > 0) {
            setTimeout(function(){
                dispatch(deleteNotification(index));
            }, item.time);
        }
        let th = this;
        setTimeout(function(){
            th.setState({
                notificationMounted: true
            })
        }, 10);
    }

    componentWillReceiveProps() {
        this.setState({
            notificationMounted: true
        })
    }

    render() {
        const {item, index} = this.props;
        return (
          <div>
            {item && this.renderNotification(item, index)}
          </div>
        )
    }
}

Notification.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Notification = connect()(Notification)

export default Notification
