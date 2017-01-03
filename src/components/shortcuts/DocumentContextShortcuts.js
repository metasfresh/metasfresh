import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentContextShortcuts extends Component {
    constructor(props){
        super(props);
        
    }
    handleShortcuts = (action, event) => {
        const {} = this.props;
        switch (action) {
            case 'FOCUS_FAST_LINE_ENTRY':
            break
            case 'REMOVE_SELECTED':
            break
        }
    }
    
    render() {
        return (
            <Shortcuts
            name={"DOCUMENT_CONTEXT"}
            handler= {this.handleShortcuts}
            targetNodeSelector={"body"}
            isolate = { true }
            preventDefault = { true }
            stopPropagation = { true } 
            />
        )
    }
}

export default DocumentContextShortcuts;