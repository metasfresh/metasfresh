import React, { Component } from 'react';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
    }
    componentDidMount = () => {
        // this.contextMenu.addEventListener("blur", ()=>{
        //     this.contextMenu.classList.remove('context-menu-open');
        // });
    }
    render() {
        const {isDisplayed} = this.props;
        return (
            !!isDisplayed && <div
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

    }
}

export default TableContextMenu
