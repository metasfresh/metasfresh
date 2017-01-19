import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';
import Prompt from '../app/Prompt';
import onClickOutside from 'react-onclickoutside';

import {
    setFilter
} from '../../actions/ListActions';

import keymap from '../../keymap.js';

import {
    setReferences,
    setActions,
    getViewActions
} from '../../actions/MenuActions';

import {
    actionsRequest,
    referencesRequest
} from '../../actions/GenericActions';

class Subheader extends Component {
    constructor(props){
        super(props);

        this.state = {
            pdfSrc: null
        }
    }

    handleClickOutside = () => {
        const { closeSubheader } = this.props;
        closeSubheader();
    }

    componentDidMount() {
        const {
            dispatch, windowType, dataId, viewId, selected, entity, query
        } = this.props;

        dispatch(setActions([]));

        document.getElementsByClassName('js-subheader-column')[0].focus();

        if(windowType){
            if(selected.length === 1 || dataId){
                dispatch(referencesRequest("window", windowType, dataId ? dataId : selected[0])).then((response) => {
                    dispatch(setReferences(response.data.references));
                });
            }

            dispatch(actionsRequest(entity, windowType, dataId ? dataId : query && query.viewId)).then((response) => {
                dispatch(setActions(response.data.actions));
            });
        }
    }

    handleReferenceClick = (type, filter) => {
        const {dispatch, closeSubheader, windowType, dataId, selected} = this.props;
        dispatch(setFilter(filter, type));
        dispatch(push(
            "/window/" + type +
            '?refType=' + windowType +
            '&refId=' + (dataId ? dataId : selected)
        ));
        closeSubheader();
    }

    handleKeyDown = (e) => {
        const {closeSubheader} = this.props;

        switch(e.key){
            case "ArrowDown":
                e.preventDefault();
                const activeElem = this.getItemActiveElem();
                if(activeElem.nextSibling) {
                    activeElem.nextSibling.focus();
                }
                break;
            case "ArrowUp":
                e.preventDefault();
                const activeEl = this.getItemActiveElem();
                if(activeEl.previousSibling) {
                    activeEl.previousSibling.focus();
                }
                break;
            case "ArrowLeft":
                e.preventDefault();
                const activeColumn = this.getColumnActiveElem();
                if(activeColumn.previousSibling) {
                    activeColumn.previousSibling.focus();
                    if(this.getItemActiveElem().nextSibling) {
                         this.getItemActiveElem().nextSibling.focus();
                    }
                }
                break;
            case "ArrowRight":
                e.preventDefault();
                const activeCol = this.getColumnActiveElem();
                if(activeCol.nextSibling) {
                    activeCol.nextSibling.focus();
                    if(this.getItemActiveElem().nextSibling){
                        this.getItemActiveElem().nextSibling.focus();
                    }
                }
                break;
            case "Enter":
                e.preventDefault();
                document.activeElement.click();
                break;
            case "Escape":
                e.preventDefault();
                closeSubheader();
                break;
        }
    }

    getColumnActiveElem = () => {
        const active = document.activeElement;
        if(active.classList.contains("js-subheader-item")) {
            return active.parentNode;
        } else {
             return active;
        }
    }

    getItemActiveElem = () => {
        const active = document.activeElement;
        if(
            active.classList.contains("js-subheader-column")
        ) {
            const child = active.childNodes;
            if(child[0] && child[0].classList.contains("js-spacer")){
                return child[0];
            }else{
                return child[1];
            }
        } else {
             return active;
        }
    }

    render() {
        const {
            windowType, onClick, references, actions, dataId, viewId, docNo, openModal,
            handlePrint, handleDelete, redirect, handleClone, dispatch, closeSubheader
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
                                <div className="js-spacer"/>
                                {windowType && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> {redirect('/window/'+ windowType +'/new'); closeSubheader()}}>
                                    <i className="meta-icon-report-1" /> New <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.NEW_DOCUMENT}</span>
                                </div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> {openModal(windowType, "window", "Advanced edit", true); closeSubheader();}}><i className="meta-icon-edit" /> Advanced Edit <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.OPEN_ADVANCED_EDIT}</span></div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> {handlePrint(windowType, dataId, docNo); closeSubheader()}}><i className="meta-icon-print" /> Print <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.OPEN_PRINT_RAPORT}</span></div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> handleClone(windowType, dataId)}><i className="meta-icon-duplicate" /> Clone</div>}
                                {dataId && <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> handleDelete()}><i className="meta-icon-delete" /> Delete <span className="tooltip-inline">{keymap.GLOBAL_CONTEXT.DELETE_DOCUMENT}</span></div>}
                                <div className="subheader-item js-subheader-item" tabIndex={0} onClick={()=> redirect('/logout')}><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column js-subheader-column" tabIndex={0}>
                                <div className="subheader-header">Actions</div>
                                <div className="subheader-break" />
                                { actions && !!actions.length ? actions.map((item, key) =>
                                    <div
                                        className="subheader-item js-subheader-item"
                                        onClick={() => {openModal(item.processId + "", "process", item.caption); closeSubheader()}}
                                        key={key}
                                        tabIndex={0}
                                    >
                                        {item.caption}
                                    </div>
                                ) : <div className="subheader-item subheader-item-disabled">There is no actions</div>}
                            </div>
                            <div className=" subheader-column js-subheader-column" tabIndex={0}>

                                    <div className="subheader-header">Referenced documents</div>
                                    <div className="subheader-break" />
                                    { references && !!references.length ? references.map((item, key) =>
                                        <div
                                            className="subheader-item js-subheader-item"
                                            onClick={() => this.handleReferenceClick(item.documentType, item.filter)}
                                            key={key}
                                            tabIndex={0}
                                        >
                                            {item.caption}
                                        </div>
                                    ) : <div className="subheader-item subheader-item-disabled">There is no referenced document</div>}

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

Subheader = connect()(onClickOutside(Subheader));

export default Subheader;
