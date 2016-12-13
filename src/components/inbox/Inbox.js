import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';

import onClickOutside from 'react-onclickoutside';

import InboxItem from './InboxItem';

import {
    markAllAsRead,
    markAsRead
} from '../../actions/AppActions';

class Inbox extends Component {
    constructor(props){
        super(props);
    }

    handleClickOutside = () => {
        const {close} = this.props;

        close && close();
    }

    handleClick = (item) => {
        const {dispatch, close} = this.props;
        if(item.target){
            switch(item.target.targetType){
                case "window":
                    dispatch(push('/window/' + item.target.documentType + '/' + item.target.documentId));
                    break;
            }
        }
        if(!item.read){
            dispatch(markAsRead(item.id));
        }
        close && close();
    }

    handleMarkAllAsRead = () => {
        const {dispatch, close} = this.props;

        dispatch(markAllAsRead());

        close && close();
    }

    handleShowAll = () => {
        const {close, dispatch} = this.props;
        dispatch(push('/inbox'));
        close && close();
    }

    render() {
        const {open, inbox, all} = this.props;
        return (
            <div>
                {(all || open) && <div className={
                    (all ? "inbox-all ": "inbox")
                }>
                    <div className={
                        "inbox-body " +
                        (!all ? "breadcrumbs-shadow" : "")
                    }>
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
                        <div className={
                            (!all ? "inbox-list" : "")
                        }>
                            {inbox && inbox.notifications.map((item, id) =>
                                <InboxItem
                                    key={id}
                                    item={item}
                                    onClick={() => this.handleClick(item)}
                                />
                            )}
                            {inbox && inbox.notifications.length == 0 &&
                                <div className="inbox-item inbox-item-empty">
                                    Inbox is empty
                                </div>
                            }
                        </div>
                        <div
                            className="inbox-footer"
                        >
                            {!all && <div
                                onClick={this.handleShowAll}
                                className="inbox-link text-xs-center"
                            >
                                Show all &gt;&gt;
                            </div>}
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
