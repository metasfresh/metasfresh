import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';
import Prompt from '../app/Prompt';

import {
    printDoc,
    openModal,
    deleteData
} from '../../actions/WindowActions';

import {
    setFilter
} from '../../actions/ListActions';

import {
    getRelatedDocuments,
    setReferences,
    getDocumentActions,
    setActions,
    getViewActions
} from '../../actions/MenuActions';

import keymap from '../../keymap.js';
import DocumentContextGlobalShortcuts from '../shortcuts/DocumentContextGlobalShortcuts';
import { ShortcutManager } from 'react-shortcuts';
const shortcutManager = new ShortcutManager(keymap);

class Subheader extends Component {
    constructor(props){
        super(props);

        this.state = {
            pdfSrc: null,
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

    getChildContext = () => {
        return { shortcuts: shortcutManager }
    }

    componentDidMount() {
        const {dispatch, windowType, dataId, viewId} = this.props;
        dispatch(setActions([]));

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


    redirect = (where) => {
        const {dispatch, onClick} = this.props;
        dispatch(push(where));
        onClick();
    }

    openModal = (windowType, type, caption, isAdvanced) => {
        const {dispatch, onClick} = this.props;
        dispatch(openModal(caption, windowType, type));
        onClick();
    }

    handleReferenceClick = (type, filter) => {
        const {dispatch, onClick, windowType, dataId} = this.props;
        dispatch(setFilter(filter, type));
        dispatch(push("/window/" + type + '?refType=' + windowType + '&refId=' + dataId));
        onClick();
    }

    handlePrint = (windowType, docId, docNo) => {
        const {dispatch, onClick} = this.props;
        const url = config.API_URL +
            '/window/' + windowType +
            '/' + docId +
            '/print/' + windowType + '_' + (docNo ? docNo : docId) + '.pdf';
        window.open(url, "_blank");
        onClick();
    }

    handleDelete = () => {
        const {dispatch} = this.props;

        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: true
            })
        }));
    }

    handlePromptCancelClick = () => {
        const {onClick} = this.props;
        onClick();
        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        }));
    }

    handlePromptSubmitClick = (windowType, docId) => {
        const {dispatch, onClick} = this.props;

        this.setState(Object.assign({}, this.state, {
            prompt: Object.assign({}, this.state.prompt, {
                open: false
            })
        }), () => {
            dispatch(deleteData(windowType, docId))
                .then(response => {
                    dispatch(push('/window/' + windowType));
                });
            }
        );
    }

    handleClone = (windowType, docId) => {
        //TODO when API ready
    }

    render() {
        const {
            windowType, onClick, references, actions, dataId, viewId, docNo
        } = this.props;
        const {prompt} = this.state;

        return (

            <div className={"subheader-container overlay-shadow subheader-open js-not-unselect"}>
                <Prompt
                    isOpen={prompt.open}
                    title={prompt.title}
                    text={prompt.text}
                    buttons={prompt.buttons}
                    onCancelClick={this.handlePromptCancelClick}
                    onSubmitClick={() => this.handlePromptSubmitClick(windowType, dataId)}
                />
                <div className="container-fluid">
                    <div className="row">
                        <div className="subheader-row">
                            <div className=" subheader-column" >
                                {windowType && <div className="subheader-item" onClick={()=> this.redirect('/window/'+ windowType +'/new')}>
                                    <i className="meta-icon-report-1" /> New
                                </div>}
                                {dataId && <div className="subheader-item" onClick={()=> this.openModal(windowType, "window", "Advanced edit", true)}><i className="meta-icon-edit" /> Advanced Edit</div>}
                                {dataId && <div className="subheader-item" onClick={()=> this.handlePrint(windowType, dataId, docNo)}><i className="meta-icon-print" /> Print</div>}
                                {dataId && <div className="subheader-item" onClick={()=> this.handleClone(windowType, dataId)}><i className="meta-icon-duplicate" /> Clone</div>}
                                {dataId && <div className="subheader-item" onClick={()=> this.handleDelete()}><i className="meta-icon-delete" /> Delete</div>}
                                <div className="subheader-item" onClick={()=> this.redirect('/logout')}><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column">
                                <div className="subheader-header">Actions</div>
                                <div className="subheader-break" />
                                { actions && !!actions.length ? actions.map((item, key) =>
                                    <div
                                        className="subheader-item"
                                        onClick={() => this.openModal(item.processId + "", "process", item.caption)}
                                        key={key}
                                    >
                                        {item.caption}
                                    </div>
                                ) : <div className="subheader-item subheader-item-disabled">There is no actions</div>}
                            </div>
                            <div className=" subheader-column">
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
                <DocumentContextGlobalShortcuts
                    openModal={this.openModal}
                    windowType={windowType}
                    handlePrint={this.handlePrint}
                    handleDelete={this.handleDelete}
                    redirect={this.redirect}
                    dataId={dataId}
                    docNo={docNo}
                />
            </div>
        )
    }
}


Subheader.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Subheader.childContextTypes = {
    shortcuts: React.PropTypes.object.isRequired
}

Subheader = connect()(Subheader);

export default Subheader;
