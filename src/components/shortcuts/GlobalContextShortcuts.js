import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class GlobalContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  _handleShortcuts = (action, event) => {
    const {handleSubheaderOpen, toggleMenuOverlay, homemenu, isMenuOverlayShow, openModal, windowType, handleSideListToggle, handleInboxOpen} = this.props;
    switch (action) {
      case 'OPEN_ACTIONS_MENU':
        handleInboxOpen(false);
        if(isMenuOverlayShow) {
          toggleMenuOverlay("", "");
        }
        handleSubheaderOpen();
        break
      case 'OPEN_NAVIGATION_MENU':
        handleInboxOpen(false);
        toggleMenuOverlay("", homemenu.nodeId);
        break
      case 'OPEN_INBOX_MENU':
        toggleMenuOverlay("", "");
        handleInboxOpen(true);
        break
      case 'OPEN_SIDEBAR_MENU':
        toggleMenuOverlay("", "");
        handleInboxOpen(false);
        handleSideListToggle();
        break
      case 'DELETE_DOCUMENT':
        break
      case 'OPEN_ADVANCED_EDIT':
        openModal(windowType + '&advanced=true', "window", "Advanced edit");
        break       
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"GLOBAL_CONTEXT"}
        handler= {this._handleShortcuts}
        targetNodeSelector={"body"}
        isolate={true}
        preventDefault={true}
      >
      </Shortcuts>
    )
  }
}

export default GlobalContextShortcuts