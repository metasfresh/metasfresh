import React, { Component } from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';

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
        const {dispatch, windowType, docId} = this.props;

        dispatch(
            attachmentsRequest('window', windowType, docId)
        ).then(response => {
            this.setState({
                data: response.data
            })
        });
    }

    toggleAttachmentDelete = (value) => {
        this.setState({
            attachmentHovered: value
        })
    }

    handleAttachmentClick = (id) => {
        const {dispatch, windowType, docId} = this.props;
        dispatch(openFile(
            'window', windowType, docId, 'attachments',
            id
        ));
    }

    handleAttachmentDelete = (e, id) => {
        const {dispatch, windowType, docId} = this.props;
        e.stopPropagation();

        dispatch(deleteRequest(
            'window', windowType, docId, null, null, 'attachments', id
        )).then(() => {
            return dispatch(attachmentsRequest(
                'window', windowType, docId
            ))
        }).then((response) => {
            this.setState({
                data: response.data
            })
        });
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
                >There is no attachment</div>
    }

    render() {
        const {data} = this.state;
        return (
            <div>
                {!data ?
                    <Loader /> :
                    this.renderData()
                }
            </div>
        );
    }
}

Attachments.PropTypes = {
    windowType: PropTypes.number.isRequired,
    docId: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired
}

Attachments = connect()(Attachments);

export default Attachments;
