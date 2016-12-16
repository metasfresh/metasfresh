import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class DocumentListContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  _handleShortcuts = (action, event) => {
    const {newDocument} = this.props;
    switch (action) {
      case 'NEW_DOCUMENT':
        newDocument();
        break
      case 'OPEN_SELECTED':
        console.log('OPEN_SELECTED');
        break
      case 'REMOVE_SELECTED':
        console.log('REMOVE_SELECTED')
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"DOCUMENT_LIST_CONTEXT"}
        handler= {this._handleShortcuts}
        targetNodeSelector={"body"}
      >
      </Shortcuts>
    )
  }
}

export default DocumentListContextShortcuts;