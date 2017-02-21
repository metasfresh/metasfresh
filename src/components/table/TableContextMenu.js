import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';

import keymap from '../../keymap.js';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            contextMenu:{
                x:0,
                y:0
            }
        }
    }

    componentDidMount() {
        const {x,y} = this.props;
        this.setPosition(x,y,this.contextMenu);
    }

    getPosition = (dir, pos, element) => {
        if(element){
            const windowSize = (dir === 'x' ? window.innerWidth : window.innerHeight);
            const elementSize = (dir === 'x' ? element.offsetWidth : element.offsetHeight);

            if (windowSize - pos > elementSize) {
                return pos;
            } else {
                return windowSize - elementSize;
            }
        }
    }

    setPosition = (x,y,elem) => {
        this.setState({
            contextMenu: {
                x: this.getPosition('x', x, elem),
                y: this.getPosition('y', y, elem)
            }
        });
    }

    render() {
        const {
            blur, selected, mainTable, handleAdvancedEdit, handleOpenNewTab, handleDelete
        } = this.props;
        const {contextMenu} = this.state;

        const isSelectedOne = selected.length === 1;
        return (
                <div
                    className="context-menu context-menu-open panel-bordered panel-primary"
                    ref={(c) => {this.contextMenu = c; c && c.focus()}}
                    style={{
                        left: contextMenu.x,
                        top: contextMenu.y
                    }}
                    tabIndex="0"
                    onBlur={blur}
                >
                {isSelectedOne && !mainTable &&
                    <div className="context-menu-item" onClick={handleAdvancedEdit}>
                        <i className="meta-icon-edit" /> Advanced edit
                        <span className="tooltip-inline">
                            {keymap.DOCUMENT_LIST_CONTEXT.ADVANCED_EDIT}
                        </span>
                    </div>
                }

                {mainTable &&
                    <div className="context-menu-item" onClick={handleOpenNewTab}>
                        <i className="meta-icon-file" /> Open in new tab
                        <span className="tooltip-inline">
                            {keymap.DOCUMENT_LIST_CONTEXT.OPEN_SELECTED}
                        </span>
                    </div>
                }

                {handleDelete && 
                    <div className="context-menu-item" onClick={handleDelete}>
                        <i className="meta-icon-trash" /> Delete
                        <span className="tooltip-inline">
                            {keymap.DOCUMENT_LIST_CONTEXT.REMOVE_SELECTED}
                        </span>
                    </div>
                }
            </div>
        )
    }
}


TableContextMenu.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableContextMenu = connect()(TableContextMenu)

export default TableContextMenu
