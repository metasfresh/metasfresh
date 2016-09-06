import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';

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

        // this.contextMenu.focus();

    }

    handleAdvancedEdit = () => {
        const {dispatch, tabId, type} = this.props;
        dispatch(openModal("Advanced edit", type + "&advanced=true", tabId));
    }
    render() {
        const {isDisplayed, x, y, blur} = this.props;
        return (
            !!isDisplayed && <div
                className="context-menu context-menu-open panel-bordered panel-primary"
                //ref={(c) => this.contextMenu = c}
                ref={function(menu) {
                      if (menu != null) {
                        menu.focus();
                      }
                    }}
                tabIndex="0" style={{left: this.props.x, top: this.props.y, display: (this.props.isDisplayed ? "block" : "none") }}
                onBlur={this.props.blur}
            >
                <div className="context-menu-item" onClick={this.handleAdvancedEdit}>
                    <i className="meta-icon-edit" /> Advanced edit
                </div>
            </div>
        )

    }
}

TableContextMenu.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableContextMenu = connect()(TableContextMenu)

export default TableContextMenu
