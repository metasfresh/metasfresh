import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';
import Prompt from '../app/Prompt';

import {
    setFilter
} from '../../actions/ListActions';

import keymap from '../../keymap.js';

import {
    getRelatedDocuments,
    setReferences,
    getDocumentActions,
    setActions,
    getViewActions
} from '../../actions/MenuActions';

class Subheader extends Component {
    constructor(props){
        super(props);

        this.state = {
            pdfSrc: null
        }
    }

    componentDidMount() {
        const {dispatch, windowType, dataId, viewId} = this.props;
        dispatch(setActions([]));

        // this.subHeader.focus();
        // document.getElementsByClassName('js-subheader-column')[0].childNodes[0].focus();
        //  document.getElementsByClassName('js-subheader-column')[0].focus();

        windowType && dataId && dispatch(getRelatedDocuments(windowType, dataId)).then((response) => {
            dispatch(setReferences(response.data.references));
        });
        (windowType && dataId) && dispatch(getDocumentActions(windowType,dataId)).then((response) => {
            dispatch(setActions(response.data.actions));
        });
        (viewId && !dataId) && dispatch(getViewActions(viewId)).then((response) => {
            dispatch(setActions(response.data.actions));
        });
    }

    handleReferenceClick = (type, filter) => {
        const {dispatch, onClick, windowType, dataId} = this.props;
        dispatch(setFilter(filter, type));
        dispatch(push("/window/" + type + '?refType=' + windowType + '&refId=' + dataId));
        onClick();
    }

    handleKeyDown = (e) => {

        // document.getElementsByClassName('js-subheader-column')[0].childNodes[0].focus();

        // let navigateList = document.getElementsByClassName('js-subheader-column')[0].childNodes;

        let columnList = document.getElementsByClassName('js-subheader-column');
        let itemList = document.getElementsByClassName('js-subheader-column')[0].childNodes;

        let lastColumnActiveElement = document.getElementsByClassName('js-subheader-column')[0];


        // console.log(columnList.length);

        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                // console.log(this.getItemActiveElem());
                let activeElem = this.getItemActiveElem();
                if(document.activeElem.nextSibling) {
                    document.activeElem.nextSibling.focus();
                }
                break;
            case "ArrowUp":
                e.preventDefault();
                // console.log(document.activeElement.previousSibling);
                if(document.activeElement.previousSibling) {
                    document.activeElement.previousSibling.focus();
                }
                break;
            case "ArrowLeft":
                e.preventDefault();
                // console.log("ArrowLeft");
                if(document.activeElement.previousSibling) {
                    document.activeElement.previousSibling.focus();
                }
                break;
            case "ArrowRight":
                e.preventDefault();
                // console.log(this.getColumnActiveElem());
                let activeCol = this.getColumnActiveElem();
                if(activeCol.nextSibling) {
                    activeCol.nextSibling.focus();
                }
                break;
            case "Enter":
                e.preventDefault();
                // console.log("Enter");
                document.activeElement.click();
                break;
        }
    }

    getColumnActiveElem = () => {
        if(document.activeElement.classList.contains("js-subheader-item")) {
            return document.activeElement.parentNode;
        } else {
             return document.activeElement;
        }
    }

    getItemActiveElem = () => {
        if(document.activeElement.classList.contains("js-subheader-column")) {
            return document.activeElement.childNodes[0];
        } else {
             return document.activeElement;
        }
    }

    render() {
        const {
            windowType, onClick, references, actions, dataId, viewId, docNo, openModal, handlePrint, handleDelete, redirect, handleClone
        } = this.props;
        const {prompt} = this.state;

        return (
            <div 
                className={"subheader-container overlay-shadow subheader-open js-not-unselect"}
                tabIndex={0}
                onKeyDown={this.handleKeyDown}
                ref={(c)=> this.subHeader = c}
            >
                <div className="container-fluid">
                    <div className="row">
                        <div className="subheader-row">
                            <div className=" subheader-column js-subheader-column" tabIndex={0}>
                                {windowType && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> redirect('/window/'+ windowType +'/new')}>
                                    <i className="meta-icon-report-1" /> New <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.NEW_DOCUMENT}</span>
                                </div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> openModal(windowType, "window", "Advanced edit", true)}><i className="meta-icon-edit" /> Advanced Edit <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.OPEN_ADVANCED_EDIT}</span></div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> handlePrint(windowType, dataId, docNo)}><i className="meta-icon-print" /> Print <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.OPEN_PRINT_RAPORT}</span></div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> handleClone(windowType, dataId)}><i className="meta-icon-duplicate" /> Clone</div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> handleDelete()}><i className="meta-icon-delete" /> Delete <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.DELETE_DOCUMENT}</span></div>}
                                <div className="subheader-item" tabIndex={0} onClick={()=> redirect('/logout')}><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column js-subheader-column" tabIndex={0}>
                                <div className="subheader-header">Actions</div>
                                <div className="subheader-break" />
                                { actions && !!actions.length ? actions.map((item, key) =>
                                    <div
                                        className="subheader-item"
                                        onClick={() => openModal(item.processId + "", "process", item.caption)}
                                        key={key}
                                    >
                                        {item.caption}
                                    </div>
                                ) : <div className="subheader-item subheader-item-disabled">There is no actions</div>}
                            </div>
                            <div className=" subheader-column js-subheader-column" tabIndex={0}>
                                <div>
                                    <div className="subheader-header">Referenced documents</div>
                                    <div className="subheader-break" />
                                    { references && !!references.length ? references.map((item, key) =>
                                        <div
                                            className="subheader-item"
                                            onClick={() => this.handleReferenceClick(item.documentType, item.filter)}
                                            key={key}
                                        >
                                            {item.caption}
                                        </div>
                                    ) : <div className="subheader-item subheader-item-disabled">There is no referenced document</div>}
                                </div>
                            </div>
                            <div className="subheader-column">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


Subheader.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Subheader = connect()(Subheader);

export default Subheader;
