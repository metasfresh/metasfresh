import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

import {
    deleteNotification
} from '../../actions/AppActions';

class Notification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            notificationMounted: false,
            isClosing: true
        }
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
    
    closeNotification = (id) => {
        const {dispatch} = this.props;
        
        this.setState({
            notificationMounted: false
        })
        
        setTimeout(function(){
            dispatch(deleteNotification(id));
        }, 300);
    }
    
    handleMouseEnter = () => {
        this.setState({
            isClosing: false
        })
    }
    
    handleMouseLeave = () => {
        this.setState({
            isClosing: true
        })
    }
    
    render() {
        const {item, index} = this.props;
        const {notificationMounted,isClosing} = this.state;
        
        return (
            <div 
                className={
                    'notification-item ' + 
                    (item.notifType ? item.notifType + ' ' : 'error ') + 
                    (notificationMounted ? 'notif-animate ' : '')
                }
                onMouseEnter={this.handleMouseEnter}
                onMouseLeave={this.handleMouseLeave}
            >
                <div className="notification-header"> 
                    {item.title} 
                    <i 
                        onClick={() => this.closeNotification(index)} 
                        className="meta-icon-close-1"
                    />
                </div>
                <div className="notification-content">
                    {item.msg}
                </div>
                {isClosing &&
                    <ReactCSSTransitionGroup 
                        transitionName="progress"
                        transitionEnterTimeout={5000} 
                        transitionLeaveTimeout={0}
                    >
                        {(notificationMounted) &&
                            <div 
                                className={
                                    'progress-bar ' +
                                    (item.notifType ? item.notifType : 'error ')
                                }
                            />
                        }
                    </ReactCSSTransitionGroup>
                }
            </div>
        )
    }
}

Notification.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Notification = connect()(Notification)

export default Notification
