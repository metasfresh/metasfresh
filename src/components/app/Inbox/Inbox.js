import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import onClickOutside from 'react-onclickoutside';

import InboxItem from './InboxItem';

class Inbox extends Component {
    constructor(props){
        super(props);
    }

    handleClickOutside = () => {
        const {close} = this.props;

        close();
    }

    render() {
        const {open} = this.props;
        return (
            <div>
                {open && <div className="inbox">
                    <div className="inbox-body breadcrumbs-shadow">
                        <div
                            className="inbox-header"
                        >
                            <span className="inbox-header-title">Inbox</span>
                            <span className="inbox-link">Mark all as read</span>
                        </div>
                        <div className="inbox-list">
                            <InboxItem unread={true}/>
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
                </div>}
            </div>
        )
    }
}

export default onClickOutside(Inbox);
