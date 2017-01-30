import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentListContextShortcuts extends Component {
    constructor(props){
        super(props);
    }
    handleShortcuts = (action, event) => {
        const {handleAdvancedEdit, handleOpenNewTab, handleDelete} = this.props;
        
        switch (action) {
        case 'OPEN_SELECTED':
            event.preventDefault();
            if(handleOpenNewTab) {
            handleOpenNewTab();
            }
            break
        case 'REMOVE_SELECTED':
            event.preventDefault();
            if(handleDelete){
            handleDelete();
            }
            break
        case 'ADVANCED_EDIT':
            event.preventDefault();
            if(handleAdvancedEdit){
            handleAdvancedEdit();
            }
            break
        }
    }

    render() {
        return (
        <Shortcuts
            name={"DOCUMENT_LIST_CONTEXT"}
            handler = { this.handleShortcuts }
            targetNodeSelector = { "body" }
            isolate = { true }
            preventDefault = { true }
            stopPropagation = { true } 
        />
        )
    }
}

export default DocumentListContextShortcuts;