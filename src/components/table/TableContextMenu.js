import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';
import update from 'react-addons-update';

import {
    openModal,
    deleteData,
    deleteLocal
} from '../../actions/WindowActions';

import Prompt from '../app/Prompt';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            prompt: {
                open: false,
                title: "Delete",
                text: "Are you sure?",
                buttons: {
                  submit: "Delete",
                  cancel: "Cancel"
                }
            }
       }
    }
    handleAdvancedEdit = () => {
        const {dispatch, tabId, type, selected} = this.props;

        dispatch(openModal("Advanced edit", type + "&advanced=true", tabId, selected[0]));
    }
    handleDelete = () => {
        const {dispatch,  tabId, type, docId, selected} = this.props;
        console.log('deleted');

        this.setState(update(this.state, {
          prompt: {
              open: {$set: true}
          }
        }))
    }

    handlePromptCancelClick = () => {
        this.setState(update(this.state, {
          prompt: {
              open: {$set: false}
          }
        }))

        this.props.blur();
    }

    handlePromptSubmitClick= () => {
       const {dispatch,  tabId, type, docId, selected} = this.props;
        this.setState(update(this.state, {
          prompt: {
              open: {$set: false}
          }
        }))

        dispatch(deleteData(type, docId, tabId, selected));
        dispatch(deleteLocal(tabId, selected, "master"));
        this.props.blur();
    }


    render() {
        const {isDisplayed, x, y, blur, selected, dispatch} = this.props;
        const style = {
            left: this.props.x,
            top: this.props.y,
            display: (isDisplayed ? "block" : "none")
        }
        const {prompt} = this.state;

        const isSelectedOne = selected.length === 1;
        return (

            !!isDisplayed &&  <div
                className="context-menu context-menu-open panel-bordered panel-primary"
                ref={(c) => c && c.focus()}
                tabIndex="0" style={style}
                onBlur={blur}
            >
                {isSelectedOne && <div className="context-menu-item" onClick={this.handleAdvancedEdit}>
                    <i className="meta-icon-edit" /> Advanced edit
                </div>}

                <div className="context-menu-item" onClick={this.handleDelete}>
                   <i className="meta-icon-edit" /> Delete
                </div>
               <Prompt
                 isOpen={prompt.open}
                 title={prompt.title}
                 text={prompt.text}
                 buttons={prompt.buttons}
                 onCancelClick={this.handlePromptCancelClick}
                 onSubmitClick={this.handlePromptSubmitClick}
               />
            </div>
        )

    }
}


TableContextMenu.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableContextMenu = connect()(TableContextMenu)

export default TableContextMenu
