import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class GlobalContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  handleShortcuts = (action, event) => {
    const {handleSubheaderOpen, toggleMenuOverlay, homemenu, isMenuOverlayShow, openModal, handlePrint, handleDelete, windowType, handleSideListToggle, handleInboxOpen} = this.props;
    switch (action) {
      case 'OPEN_ACTIONS_MENU':
        event.preventDefault();
        handleInboxOpen(false);
        if(isMenuOverlayShow) {
          toggleMenuOverlay("", "");
        }
        handleSubheaderOpen();
        break
      case 'OPEN_NAVIGATION_MENU':
        event.preventDefault();
        handleInboxOpen(false);
        toggleMenuOverlay("", homemenu.nodeId);
        break
      case 'OPEN_INBOX_MENU':
        event.preventDefault();
        toggleMenuOverlay("", "");
        handleInboxOpen(true);
        break
      case 'OPEN_SIDEBAR_MENU':
        event.preventDefault();
        toggleMenuOverlay("", "");
        handleInboxOpen(false);
        handleSideListToggle();
        break
      case 'DELETE_DOCUMENT':
        handleDelete();
        break
      case 'OPEN_ADVANCED_EDIT':
        event.preventDefault();
        openModal();
        break
      case 'OPEN_PRINT_RAPORT':
        event.preventDefault();
        handlePrint();
        break       
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"GLOBAL_CONTEXT"}
        handler= {this.handleShortcuts}
        targetNodeSelector={"body"}
        isolate={true}
        preventDefault={true}
        stopPropagation={true}
      >
      </Shortcuts>
    )
  }
}

export default GlobalContextShortcuts