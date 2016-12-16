import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class GlobalContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  _handleShortcuts = (action, event) => {
    const {handleSubheaderOpen, toggleMenuOverlay, homemenu, isMenuOverlayShow, openModal, windowType} = this.props;
    switch (action) {
      case 'OPEN_ACTIONS_MENU':
        if(isMenuOverlayShow) {
          toggleMenuOverlay("", "");
        }
        handleSubheaderOpen();
        break
      case 'OPEN_NAVIGATION_MENU':
        toggleMenuOverlay("", homemenu.nodeId);
        break
      case 'OPEN_INBOX_MENU':
        console.log('OPEN_INBOX_MENU')
        break
      case 'OPEN_SIDEBAR_MENU':
        console.log('OPEN_SIDEBAR_MENU')
        break
      case 'DELETE_DOCUMENT':
        console.log('DELETE_DOCUMENT')
        break
      case 'OPEN_ADVANCED_EDIT':
        openModal(windowType + '&advanced=true', "window", "Advanced edit");
        console.log('OPEN_ADVANCED_EDIT')
        break       
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"GLOBAL_CONTEXT"}
        handler= {this._handleShortcuts}
        targetNodeSelector={"body"}
      >
      </Shortcuts>
    )
  }
}

export default GlobalContextShortcuts