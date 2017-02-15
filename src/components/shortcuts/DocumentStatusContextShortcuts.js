import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class PaginationContextShortcuts extends Component {
    handleShortcuts = (action) => {
        const {
            handleDocumentCompleteStatus
        } = this.props;

        switch (action) {
            case 'COMPLETE_STATUS':
                return handleDocumentCompleteStatus();
        }
    }

    render() {
        return (
            <Shortcuts
                name={"DOCUMENT_STATUS_CONTEXT"}
                handler = { this.handleShortcuts }
                targetNodeSelector = { "body" }
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
            />
        )
    }
}

export default PaginationContextShortcuts;
