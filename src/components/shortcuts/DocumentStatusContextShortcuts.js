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
                name="DOCUMENT_STATUS_CONTEXT"
                handler = { this.handleShortcuts }
                targetNodeSelector = "body"
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
                dataShortcuts = { true }
            />
        )
    }
}

export default DocumentStatusContextShortcuts;
