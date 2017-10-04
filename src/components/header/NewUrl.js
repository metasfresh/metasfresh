// import counterpart from 'counterpart';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { addNotification } from '../../actions/AppActions';
import { createUrlAttachment } from '../../actions/AppActions';

class NewUrl extends Component {
    state = { url: '' }

    handleChange = ({ target: { value: url } }) => {
        this.setState({ url });
    }

    handleClick = () => {
        const {
          windowId, documentId, handleAddUrlClose, dispatch,
        } = this.props;
        const { url } = this.state;

        // extract name from URL by getting part after last / and before ? or #
        // TODO: handle edge cases like URL with trailing slash
        const name = url.split('/').pop().split('#')[0].split('?')[0];

        // TODO: Add translations for notifications
        createUrlAttachment({ windowId, documentId, url, name }).then(() => {
            handleAddUrlClose();

            dispatch(addNotification(
                'Attachment', 'URL has been added.', 5000, 'success',
            ));
        }).catch(() => {
            dispatch(addNotification(
                'Attachment', 'URL could not be added!', 5000, 'error',
            ));
        });
    }

    render() {
        const { handleAddUrlClose } = this.props;
        const { url } = this.state;

        return (
            <div className="screen-freeze">
                <div
                    className="panel panel-modal panel-attachurl panel-modal-primary"
                >
                    <div className="panel-attachurl-header-wrapper">
                        <div
                            className="panel-attachurl-header panel-attachurl-header-top"
                        >
                            <span className="attachurl-headline">
                                Add URL
                                {/* TODO: Add translation */}
                            </span>
                            <div
                                className="input-icon input-icon-lg attachurl-icon-close"
                                onClick={handleAddUrlClose}
                            >
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-attachurl-body">
                        <textarea
                            value={url}
                            onChange={this.handleChange}
                        />
                    </div>
                    <div className="panel-attachurl-footer">
                        <button
                            onClick={this.handleClick}
                            className="btn btn-meta-success btn-sm btn-submit"
                        >
                            Create
                            {/* TODO: Add translation */}
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

NewUrl = connect()(NewUrl);

export default NewUrl;
