import React, { Component } from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import Loader from '../app/Loader';

import {
    attachmentsRequest,
    openFile,
    deleteRequest
} from '../../actions/GenericActions';
class Attachments extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null,
            attachmentHovered: null
        }
    }

    componentDidMount = () => {
        const {windowType, docId} = this.props;

        attachmentsRequest('window', windowType, docId)
            .then(response => {
                this.setState({
                    data: response.data
                }, () => {
                    this.attachments && this.attachments.focus();
                })
            });
    }

    toggleAttachmentDelete = (value) => {
        this.setState({
            attachmentHovered: value
        })
    }

    handleAttachmentClick = (id) => {
        const {windowType, docId} = this.props;
        openFile('window', windowType, docId, 'attachments', id);
    }

    handleAttachmentDelete = (e, id) => {
        const {windowType, docId} = this.props;
        e.stopPropagation();

        deleteRequest(
            'window', windowType, docId, null, null, 'attachments', id
        ).then(() => {
            return attachmentsRequest(
                'window', windowType, docId
            )
        }).then((response) => {
            this.setState({
                data: response.data
            })
        });
    }

    handleKeyDown = (e) => {
        const active = document.activeElement;

        const keyHandler = (e, dir) => {
            const sib = dir ? 'nextSibling' : 'previousSibling';
            e.preventDefault();
            if(active.classList.contains('js-subheader-item')){
                active[sib] && active[sib].focus();
            }else{
                active.childNodes[0].focus();
            }
        }

        switch(e.key){
            case 'ArrowDown':
                keyHandler(e, true);
                break
            case 'ArrowUp':
                keyHandler(e, false);
                break;
            case 'Enter':
                e.preventDefault();
                document.activeElement.click();
                break;
        }
    }

    renderData = () => {
        const {data, attachmentHovered} = this.state;

        return (data && data.length) ?
            data.map((item, key) =>
                <div
                    className="subheader-item subheader-item-ellipsis js-subheader-item"
                    key={key}
                    tabIndex={0}
                    onMouseEnter={() =>
                        this.toggleAttachmentDelete(item.id)}
                    onMouseLeave={() =>
                        this.toggleAttachmentDelete(null)}
                    onClick={() =>
                        this.handleAttachmentClick(item.id)}
                >
                    {item.name}
                    {attachmentHovered === item.id &&
                        <div
                            className="subheader-additional-box"
                            onClick={(e) =>
                                this.handleAttachmentDelete(
                                    e, item.id
                                )
                            }
                        >
                            <i className="meta-icon-delete"/>
                        </div>
                    }
                </div>
            ) :
                <div
                    className="subheader-item subheader-item-disabled"
                >{counterpart.translate(
                    'window.sideList.attachments.empty'
                )}</div>
    }

    render() {
        const {data} = this.state;
        return (
            <div
                onKeyDown={this.handleKeyDown}
                ref={c => this.attachments = c}
                tabIndex={0}
            >
                {!data ?
                    <Loader /> :
                    this.renderData()
                }
            </div>
        );
    }
}

Attachments.PropTypes = {
    windowType: PropTypes.string.isRequired,
    docId: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired
}

Attachments = connect()(Attachments);

export default Attachments;
