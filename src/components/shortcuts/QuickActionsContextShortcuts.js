import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class QuickActionsContextShortcuts extends Component {
    constructor(props){
        super(props);
    }
    handleShortcuts = (action, event) => {
        const {handleClick, onClick} = this.props;

        switch (action) {
        case 'QUICK_ACTION_POS':
            event.preventDefault();
            handleClick();
            break
        case 'QUICK_ACTION_TOGGLE':
            event.preventDefault();
            onClick();
            break
        }
    }

    render() {
        return (
        <Shortcuts
            name="QUICK_ACTIONS"
            handler = { this.handleShortcuts }
            targetNodeSelector = "body"
            isolate = { true }
            global = { true }
            preventDefault = { true }
            stopPropagation = { true }
        />
        )
    }
}

export default QuickActionsContextShortcuts;
