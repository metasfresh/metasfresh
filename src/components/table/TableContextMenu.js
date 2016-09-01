import React, { Component } from 'react';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);

        console.log("this.props");
        console.log(this.props);
    }
    componentDidMount = () => {
        this.contextMenu.addEventListener("blur", ()=>{
            this.contextMenu.classList.remove('context-menu-open');
        });
    }
    handleContextMenu = () => {
      console.log('handleContextMenu');
        this.contextMenu.classList.add('context-menu-open');
        this.contextMenu.style.left = e.clientX + "px";
        this.contextMenu.style.top = e.clientY + "px";
        this.contextMenu.focus();
    }
    render() {

        // if(this.props.isDisplayed){
          return (

              <div
                  className="context-menu panel-bordered panel-primary"
                  ref={(c) => this.contextMenu = c}
                  tabIndex="0" style={{left: this.props.x, top: this.props.y, display: (this.props.isDisplayed ? "block" : "none") }}
              >
                  <div className="context-menu-item">
                      <i className="meta-icon-file" /> Change log
                  </div>
                  <div className="context-menu-item">
                      <i className="meta-icon-trash" /> Remove selected
                  </div>
              </div>

          )
        // }else{
        //   return false
        // }
    }
}

export default TableContextMenu
