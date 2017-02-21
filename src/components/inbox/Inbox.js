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
                case 'window':
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

    componentDidUpdate() {
        const {open} = this.props;
        const inboxWrapper = document.getElementsByClassName('js-inbox-wrapper')[0];
        if(inboxWrapper && open){
            inboxWrapper.focus();
        }
    }

    handleKeyDown = (e) => {
        const {close} = this.props;
        const inboxItem = document.getElementsByClassName('js-inbox-item')[0];
        switch(e.key){
            case 'ArrowDown':
                e.preventDefault();
                if (document.activeElement.classList.contains('js-inbox-wrapper')) {
                    if(inboxItem){
                        inboxItem.focus();
                    }
                    
                }
                break;
            case 'Escape':
                e.preventDefault();
                close && close();
                break;
        }
    }

    render() {
        const {open, inbox, all, close} = this.props;
        return (
            <div className="js-inbox-wrapper" onKeyDown={(e) => this.handleKeyDown(e)} tabIndex={0}>
                {(all || open) && <div className={
                    (all ? 'inbox-all ': 'inbox')
                }>
                    <div className={
                        'inbox-body ' +
                        (!all ? 'breadcrumbs-shadow' : '')
                    }
                    
                    >
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
                            (!all ? 'inbox-list' : '')
                        }>
                            {inbox && inbox.notifications.map((item, id) =>
                                <InboxItem
                                    key={id}
                                    item={item}
                                    close={close}
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
