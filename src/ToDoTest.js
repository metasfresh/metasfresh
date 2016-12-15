import React, { Component, PropTypes } from 'react';
import { Shortcuts } from 'react-shortcuts';
 
class TodoItem extends React.Component {
  _handleShortcuts = (action, event) => {
    switch (action) {
      case 'MOVE_LEFT':
        console.log('moving left')
        break
      case 'MOVE_RIGHT':
        console.log('moving right')
        break
      case 'MOVE_UP':
        console.log('moving up')
        break
      case 'COPY':
        console.log('copying stuff')
        break
    }
  }
 
  render() {
    return (
      <Shortcuts
        // name: 'TODO_ITEM',
        // handler: {this._handleShortcuts},
      >
        <div>Make something amazing today</div>
      </Shortcuts>
    )
  }
}