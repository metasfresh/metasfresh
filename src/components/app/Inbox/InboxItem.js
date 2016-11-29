import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Moment from 'moment';

class InboxItem extends Component {
    constructor(props){
        super(props);
    }

    renderIconFromTarget = (target) => {
        switch(target){
            case "143":
                return "sales";
                break;
            default:
                return "system";
        }
    }

    render() {
        const {item, onClick} = this.props;
        return (
            <div
                onClick={onClick}
                className={
                "inbox-item pointer " +
                (!item.read ? "inbox-item-unread ":"")
            }>
                <div className="inbox-item-icon">
                    <i className={"meta-icon-" + this.renderIconFromTarget(item.target && item.target.documentType)} />
                </div>
                <div className="inbox-item-content">
                    <div className="inbox-item-title">
                        {item.message}
                    </div>
                    <div className="inbox-item-footer">
                        <span>{Moment(item.timestamp).fromNow()}</span>
                        <span>Notification</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default InboxItem;
