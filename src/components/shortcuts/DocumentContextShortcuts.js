import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  handleShortcuts = (action, event) => {
    const {} = this.props;
    switch (action) {
      case 'OPEN_PRINT_RAPORT':
        break
      case 'OPEN_ADVANCED_EDIT':
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"DOCUMENT_CONTEXT"}
        handler= {this.handleShortcuts}
        targetNodeSelector={"body"}
      >
      </Shortcuts>
    )
  }
}

export default DocumentContextShortcuts;