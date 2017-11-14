import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class TableContextShortcuts extends Component {
    constructor(props){
        super(props);
    }

    handleShortcuts = (action) => {
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
                alwaysFireHandler
                global
                handler={this.handleShortcuts}
                isolate
                name="TABLE_CONTEXT"
                preventDefault
                stopPropagation
                targetNodeSelector="body"
            />
        );
    }
}

export default TableContextShortcuts;
