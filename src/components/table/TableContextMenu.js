import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    openModal
} from '../../actions/WindowActions';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
    }
    handleAdvancedEdit = () => {
        const {dispatch, tabId, type, selected} = this.props;

        dispatch(openModal("Advanced edit", type + "&advanced=true", tabId, selected[0]));
    }
    render() {
        const {isDisplayed, x, y, blur, selected} = this.props;
        const style = {
            left: this.props.x,
            top: this.props.y,
            display: (isDisplayed ? "block" : "none")
        }

        const isSelectedOne = selected.length === 1;
        return (
            !!isDisplayed && <div
                className="context-menu context-menu-open panel-bordered panel-primary"
                ref={(c) => c && c.focus()}
                tabIndex="0" style={style}
                onBlur={blur}
            >
                {isSelectedOne && <div className="context-menu-item" onClick={this.handleAdvancedEdit}>
                    <i className="meta-icon-edit" /> Advanced edit
                </div>}
            </div>
        )

    }
}

TableContextMenu.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableContextMenu = connect()(TableContextMenu)

export default TableContextMenu
