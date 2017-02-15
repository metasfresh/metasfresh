import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Moment from 'moment';

class InboxItem extends Component {
    constructor(props){
        super(props);
    }

    renderIconFromTarget = (target) => {
        switch(target){
            case '143':
                return 'sales';
                break;
            default:
                return 'system';
        }
    }

    componentDidMount() {
        document.getElementsByClassName('js-inbox-wrapper')[0].focus();
    }

     handleKeyDown = (e) => {
        const {close} = this.props;
        switch(e.key){
            case 'ArrowDown':
                e.preventDefault();
                if(document.activeElement.nextSibling) {
                    document.activeElement.nextSibling.focus();
                }
                break;
            case 'ArrowUp':
                e.preventDefault();
                if(document.activeElement.previousSibling) {
                    document.activeElement.previousSibling.focus();
                }
                break;
            case 'Enter':
                e.preventDefault();
                document.activeElement.click();
                break;
            case 'Escape':
                e.preventDefault();
                close && close();
                break;
        }
    }

    render() {
        const {item, onClick} = this.props;
        return (
            <div
                onClick={onClick}
                onKeyDown={this.handleKeyDown}
                tabIndex={0}
                className={
                'inbox-item js-inbox-item pointer ' +
                (!item.read ? 'inbox-item-unread ':'')
            }>
                {item.important && <div className="inbox-item-icon inbox-item-icon-sm">
                    <i className="meta-icon-important" />
                </div>}
                <div className="inbox-item-icon">
                    <i className={'meta-icon-' + this.renderIconFromTarget(item.target && item.target.documentType)} />
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
