import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';


class InboxItem extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {unread} = this.props;
        return (
            <div className={
                "inbox-item " +
                (unread ? "inbox-item-unread ":"")
            }>
                <div className="inbox-item-icon">
                    <i className="meta-icon-calendar" />
                </div>
                <div className="inbox-item-content">
                    <div className="inbox-item-title">
                        The Material Receipt <b>123123</b> for Partner <b>G088F Mustermann</b> has been created.
                    </div>
                    <div className="inbox-item-footer">
                        <span>Today, 14:16</span>
                        <span>Notification</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default InboxItem;
