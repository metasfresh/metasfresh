import React, { Component } from 'react';
import MenuOverlayItem from './MenuOverlayItem';

class MenuOverlayContainer extends Component {
  constructor(props){
      super(props);
  }

  render() {
      const {children, caption, handleClickOnFolder} = this.props;
      return (
          <div className="menu-overlay-node-container">
              <p className="menu-overlay-header">{caption}</p>
              {children && children.map((subitem, subindex) =>
                  <MenuOverlayItem
                      key={subindex}
                      handleClickOnFolder={handleClickOnFolder}
                      {...subitem}
                  />
              )}
          </div>
      )
  }
}

export default MenuOverlayContainer
