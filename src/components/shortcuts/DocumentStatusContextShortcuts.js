import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class DocumentStatusContextShortcuts extends Component {
    handleShortcuts = (action, event) => {
        const {
            handleDocumentCompleteStatus
        } = this.props;

        switch (action) {
            case 'COMPLETE_STATUS':
                event.preventDefault();
                return handleDocumentCompleteStatus();
        }
    }

    render() {
        return (
            <Shortcuts
                alwaysFireHandler
                handler={this.handleShortcuts}
                isolate
                name="DOCUMENT_STATUS_CONTEXT"
                preventDefault
                stopPropagation
                targetNodeSelector="body"
            />
        );
    }
}

export default DocumentStatusContextShortcuts;
