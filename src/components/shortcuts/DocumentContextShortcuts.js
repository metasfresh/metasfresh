import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  _handleShortcuts = (action, event) => {
    const {} = this.props;
    switch (action) {
      case 'OPEN_PRINT_RAPORT':
        console.log('OPEN_SELECTED');
        break
      case 'OPEN_ADVANCED_EDIT':
        console.log('REMOVE_SELECTED')
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"DOCUMENT_CONTEXT"}
        handler= {this._handleShortcuts}
        targetNodeSelector={"body"}
      >
      </Shortcuts>
    )
  }
}

export default DocumentContextShortcuts;