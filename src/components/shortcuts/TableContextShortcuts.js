import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';

class TableContextShortcuts extends Component {
    constructor(props){
        super(props);
    }

    handleShortcuts = (action, event) => {
        const {
            handleToggleExpand, handleToggleQuickInput
        } = this.props;

        switch (action) {
            case 'TOGGLE_EXPAND':
                handleToggleExpand();
                break;
            case 'TOGGLE_QUICK_INPUT':
                handleToggleQuickInput();
                break;
        }
    }

    render() {
        return (
            <Shortcuts
                name="TABLE_CONTEXT"
                handler = { this.handleShortcuts }
                targetNodeSelector = "body"
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
            />
        )
    }
}

export default TableContextShortcuts;
