import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import onClickOutside from 'react-onclickoutside';

import InboxItem from './InboxItem';

class Inbox extends Component {
    constructor(props){
        super(props);
    }

    handleClickOutside = () => {

    }

    render() {
        return (
            <div className="inbox">
                <div className="inbox-caption">
                    <span className="notification">
                        <i className="meta-icon-notifications"/>
                        <span className="notification-number">4</span>
                    </span>
                </div>
                <div className="inbox-body breadcrumbs-shadow">
                    <div
                        className="inbox-header"
                    >
                        <span className="inbox-header-title">Inbox</span>
                        <span className="inbox-link">Mark all as read</span>
                    </div>
                    <div className="inbox-list">
                        <InboxItem />
                        <InboxItem />
                        <InboxItem />
                        <InboxItem />
                    </div>
                    <div
                        className="inbox-footer"
                    >
                        <span />
                        <span className="inbox-link">Show all &gt;&gt;</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default onClickOutside(Inbox);
