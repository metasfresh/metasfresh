import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class TodoItem extends Component {
  _handleShortcuts = (action, event) => {
    switch (action) {
      case 'MOVE_DOWN':
        console.log('moving bottom')
        break
      case 'MOVE_LEFT':
        console.log('moving left')
        break
      case 'MOVE_RIGHT':
        console.log('moving right')
        break
      case 'MOVE_UP':
        console.log('moving up')
        break
      case 'OPEN_ACTIONS_MENU':
        console.log('OPEN_ACTIONS_MENU')
        break
      case 'COPY':
        console.log('copying stuff')
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        name={"TODO_ITEM"}
        handler= {this._handleShortcuts}
        targetNodeSelector={"body"}
      >
        <div>Make something amazing today</div>
      </Shortcuts>
    )
  }
}

export default TodoItem