import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class TabsContextShortcuts extends Component {
  constructor(props){
        super(props);
        
    }
  handleShortcuts = (action, event) => {
    const {} = this.props;
    console.log(event);
    switch (action) {
      case 'OPEN_ACTIONS_MENU':
        console.log('shortcut 1');
        event.preventDefault();
        break
      case 'OPEN_NAVIGATION_MENU':
        console.log('shortcut 1');
        break
      case 'OPEN_INBOX_MENU':
        console.log('shortcut 1');
        break
      case 'OPEN_SIDEBAR_MENU':
        console.log('shortcut 1');
        break    
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"GLOBAL_CONTEXT"}
        handler= {this.handleShortcuts}
        targetNodeSelector={".tab-content"}
        isolate={true}
        preventDefault={true}
      >
      </Shortcuts>
    )
  }
}

export default TabsContextShortcuts