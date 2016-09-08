import React, { Component,PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    openModal,
    openPrompt,
    deleteData,
    deleteRows
} from '../../actions/WindowActions';

import Prompt from '../app/Prompt';

class TableContextMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            prompt: {
                open: false
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
        
        this.setState({prompt: {
            open: true
        }})
        // dispatch(openPrompt("Title", "Text" ));
        // dispatch(deleteData(type, docId, tabId, selected[0]));
      
    }

    handlePromptCancelClick = () => {
        alert("cancel clicked");
        this.setState({prompt: {
            open: false
        }})
    }

    handlePromptOkClick= () => {
       const {dispatch,  tabId, type, docId, selected} = this.props;
        alert("ok clicked");
        this.setState({prompt: {
            open: false
        }})
        dispatch(deleteRows(tabId, selected, "master"));

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
               onCancelClick={this.handlePromptCancelClick}
               onOkClick={this.handlePromptOkClick}
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
