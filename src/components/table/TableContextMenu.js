import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';
import update from 'react-addons-update';

import {
    openModal,
    deleteData,
    deleteLocal
} from '../../actions/WindowActions';

import Prompt from '../app/Prompt';

import keymap from '../../keymap.js';
import DocumentListContextShortcuts from '../shortcuts/DocumentListContextShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            contextMenu:{
                x:0,
                y:0
            },
            prompt: {
                open: false
            }
        }
    }

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    componentDidMount() {
        const {x,y} = this.props;
        this.setPosition(x,y,this.contextMenu);
    }

    handleAdvancedEdit = () => {
        const {dispatch, tabId, type, selected} = this.props;

        dispatch(openModal("Advanced edit", type, "window", tabId, selected[0], true));
    }

    handleDelete = () => {
        this.setState(update(this.state, {
            prompt: {
                open: {$set: true}
            }
        }));
    }

    handleOpenNewTab = () => {
        const {type, selected} = this.props;
        window.open("/window/" + type + "/" + selected[0], "_blank");
    }

    handlePromptCancelClick = () => {
        this.setState(update(this.state, {
            prompt: {
                open: {$set: false}
            }
        }))

        this.props.blur();
    }


    handlePromptSubmitClick = () => {
        const {dispatch,  tabId, type, docId, selected, deselect, mainTable, updateDocList} = this.props;
        this.setState(update(this.state, {
            prompt: {
                open: {$set: false}
            }
        }))

        if(mainTable){
            if(selected.length>1){
                for(let i=0;i<selected.length;i++){
                    dispatch(deleteData(type, selected[i]));
                }
                updateDocList();
                deselect();
            } else {
                dispatch(deleteData(type, selected))
                .then(response => {
                    updateDocList();
                }).then(response => {
                    deselect();
                });
            }

        } else {
            dispatch(deleteData(type, docId, tabId, selected))
            .then(response => {
                dispatch(deleteLocal(tabId, selected, "master"))
            }).then(response => {
                deselect();
            });
        }

        this.props.blur();

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
        this.setState(Object.assign({}, this.state, {
            contextMenu: {
                x: this.getPosition('x', x, elem),
                y: this.getPosition('y', y, elem)
            }
        }));
    }

    render() {
        const {
            isDisplayed, x, y, blur, selected, dispatch, mainTable
        } = this.props;
        const {prompt, contextMenu} = this.state;

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
                    <div className="context-menu-item" onClick={this.handleAdvancedEdit}>
                        <i className="meta-icon-edit" /> Advanced edit
                    </div>
                }

                <div className="context-menu-item" onClick={this.handleOpenNewTab}>
                    <i className="meta-icon-file" /> Open in new tab
                </div>
                <div className="context-menu-item" onClick={this.handleDelete}>
                    <i className="meta-icon-edit" /> Delete
                </div>
                {
                    prompt.open &&
                    <Prompt
                        title={"Delete"}
                        text={"Are you sure?"}
                        buttons={{submit: "Delete", cancel: "Cancel"}}
                        onCancelClick={this.handlePromptCancelClick}
                        onSubmitClick={this.handlePromptSubmitClick}
                    />
                }
                
                <DocumentListContextShortcuts
                    handleOpenNewTab={this.handleOpenNewTab}
                    handleDelete={this.handleDelete}
                />
            </div>
        )
    }
}

TableContextMenu.childContextTypes = {
    shortcuts: PropTypes.object.isRequired
}


TableContextMenu.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableContextMenu = connect()(TableContextMenu)

export default TableContextMenu
