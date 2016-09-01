import React, { Component } from 'react';

import {
    openModal
} from '../../actions/WindowActions';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
    }
    componentDidMount = () => {
        // this.contextMenu.addEventListener("blur", ()=>{
        //     this.contextMenu.classList.remove('context-menu-open');
        // });
    }
    handleAdvancedEdit = () => {
        const {dispatch, tabid} = this.props;
        dispatch(openModal(windowType + "&tabid=" + tabid + "&advanced=true"));
    }
    render() {
        const {isDisplayed, x, y} = this.props;
        return (
            !!isDisplayed && <div
                className="context-menu context-menu-open panel-bordered panel-primary"
                ref={(c) => this.contextMenu = c}
                tabIndex="0" style={{left: this.props.x, top: this.props.y, display: (this.props.isDisplayed ? "block" : "none") }}
            >
                <div className="context-menu-item">
                    <i className="meta-icon-edit" /> Advanced edit
                </div>
            </div>
        )

    }
}

export default TableContextMenu
