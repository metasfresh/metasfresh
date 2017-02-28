import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    deleteNotification
} from '../../actions/AppActions';

class Notification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isClosing: false
        }

        this.closing = null;
    }

    componentDidMount() {
        const {item} = this.props;

        if(item.time > 0) {
            this.setClosing();
        }

        const th = this;
        setTimeout(() => {
            th.setState({
                isClosing: true
            })
        }, 10);
    }

    setClosing = () => {
        const {dispatch, item} = this.props;
        this.closing = setTimeout(() => {
            dispatch(deleteNotification(item.id));
        }, item.time);
    }

    handleCloseButton = () => {
        const {dispatch, item} = this.props;

        this.closing && clearInterval(this.closing);

        dispatch(deleteNotification(item.id));
    }

    handleClosing = (shouldClose) => {
        shouldClose ?
            this.setClosing() :
            clearInterval(this.closing);

        this.setState({
            isClosing: shouldClose
        })
    }

    render() {
        const {item} = this.props;
        const {isClosing} = this.state;

        return (
            <div
                className={
                    'notification-item ' +
                    (item.notifType ? item.notifType + ' ' : 'error ')
                }
                onMouseEnter={() => this.handleClosing(false)}
                onMouseLeave={() => this.handleClosing(true)}
            >
                <div className="notification-header">
                    {item.title}
                    <i
                        onClick={() => this.handleCloseButton()}
                        className="meta-icon-close-1"
                    />
                </div>
                <div className="notification-content">
                    {item.msg}
                </div>
                <div
                    className={
                        'progress-bar ' +
                        (item.notifType ? item.notifType : 'error ')
                    }
                    style={
                        isClosing ?
                            {width: 0, transition: 'width 5s linear'} :
                            {width: '100%', transition: 'width 0.2s linear'}
                    }
                />
            </div>
        )
    }
}

Notification.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Notification = connect()(Notification)

export default Notification
