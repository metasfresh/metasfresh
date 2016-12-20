import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentContextGlobalShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  handleShortcuts = (action, event) => {
    const {openModal, windowType, handlePrint, handleDelete, redirect, dataId, docNo} = this.props;
    switch (action) {
      case 'DELETE_DOCUMENT':
        handleDelete();
        break
      case 'NEW_DOCUMENT':
        redirect('/window/'+ windowType +'/new');
        break 
      case 'OPEN_ADVANCED_EDIT':
        openModal(windowType + '&advanced=true', "window", "Advanced edit");
        break 
      case 'OPEN_PRINT_RAPORT':
        handlePrint(windowType, dataId, docNo);
        break
      case 'FOCUS_FAST_LINE_ENTRY':
        break       
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"DOCUMENT_CONTEXT"}
        handler= {this.handleShortcuts}
        targetNodeSelector={"body"}
        preventDefault={true}
      >
      </Shortcuts>
    )
  }
}

export default DocumentContextGlobalShortcuts