import React, { Component } from 'react';

class MenuOverlayItem extends Component {
  constructor(props){
      super(props);
  }

  render() {
      const {nodeId, caption, children, handleClickOnFolder} = this.props;
      return (
          <span
              className={
                  "menu-overlay-expanded-link " +
                  (children ? "menu-overlay-expand" : "menu-overlay-link")
              }
              onClick={e => children && handleClickOnFolder(e, nodeId)}
          >
              {caption}
          </span>
      )
  }
}

export default MenuOverlayItem
