import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import onClickOutside from 'react-onclickoutside';

import InboxItem from './InboxItem';

import {markAllAsRead} from '../../../actions/AppActions';

class Inbox extends Component {
    constructor(props){
        super(props);
    }

    handleClickOutside = () => {
        const {close} = this.props;

        close();
    }

    handleClick = () => {
        const {close} = this.props;

        close();
    }

    handleMarkAllAsRead = () => {
        const {dispatch, close} = this.props;

        dispatch(markAllAsRead());

        close();
    }

    handleShowAll = () => {
        const {close} = this.props;

        close();
    }

    render() {
        const {open, inbox} = this.props;
        return (
            <div>
                {open && <div className="inbox">
                    <div className="inbox-body breadcrumbs-shadow">
                        <div
                            className="inbox-header"
                        >
                            <span className="inbox-header-title">Inbox</span>
                            <span
                                onClick={this.handleMarkAllAsRead}
                                className="inbox-link"
                            >
                                Mark all as read
                            </span>
                        </div>
                        <div className="inbox-list">
                            {inbox && inbox.notifications.map((item, id) =>
                                <InboxItem
                                    key={id}
                                    item={item}
                                    onClick={this.handleClick}
                                />
                            )}
                            {inbox && inbox.notifications.length == 0 &&
                                <div className="inbox-item">
                                    There is no notifications.
                                </div>
                            }
                        </div>
                        <div
                            className="inbox-footer"
                        >
                            <div
                                onClick={this.handleShowAll}
                                className="inbox-link text-xs-center"
                            >
                                Show all &gt;&gt;
                            </div>
                        </div>
                    </div>
                </div>}
            </div>
        )
    }
}

Inbox.propTypes = {
	dispatch: PropTypes.func.isRequired
};

Inbox = connect()(onClickOutside(Inbox))

export default Inbox;
