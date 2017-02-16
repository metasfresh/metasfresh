import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentListContextShortcuts extends Component {
    constructor(props){
        super(props);
    }
    handleShortcuts = (action, event) => {
        const {handleClick} = this.props;
        
        switch (action) {
        case 'QUICK_ACTION_POS':
            event.preventDefault();
            handleClick();
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
            preventDefault = { true }
            stopPropagation = { true } 
        />
        )
    }
}

export default DocumentListContextShortcuts;
