import React, { Component } from 'react';

import {
    openModal
} from '../../actions/WindowActions';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
    }
    componentDidMount = () => {
        this.contextMenu.addEventListener("blur", ()=>{
            this.contextMenu.classList.remove('context-menu-open');
        });
    }
    handleContextMenu = () => {
        this.contextMenu.classList.add('context-menu-open');
        this.contextMenu.style.left = e.clientX + "px";
        this.contextMenu.style.top = e.clientY + "px";
        this.contextMenu.focus();
    }
    handleAdvancedEdit = () => {
        const {dispatch, tabid} = this.props;
        dispatch(openModal(windowType + "&tabid=" + tabid + "&advanced=true"));
    }
    render() {
        return (
            <div
                className="context-menu panel-bordered panel-primary"
                ref={(c) => this.contextMenu = c}
                tabIndex="0"
            >
                <div className="context-menu-item">
                    <i className="meta-icon-edit" /> Advanced edit
                </div>
            </div>
        )
    }
}

export default TableContextMenu
