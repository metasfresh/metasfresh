import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentListContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  handleShortcuts = (action, event) => {
    const {handleOpenNewTab, handleDelete} = this.props;
    switch (action) {
      case 'OPEN_SELECTED':
        handleOpenNewTab();
        break
      case 'REMOVE_SELECTED':
        handleDelete();
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"DOCUMENT_LIST_CONTEXT"}
        handler= {this.handleShortcuts}
        targetNodeSelector={"body"}
      >
      </Shortcuts>
    )
  }
}

export default DocumentListContextShortcuts;