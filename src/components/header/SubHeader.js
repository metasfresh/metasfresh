import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import {push} from 'react-router-redux';
import '../../assets/css/header.css';
import Prompt from '../app/Prompt';

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

    render() {
        const {
            windowType, onClick, references, actions, dataId, viewId, docNo, openModal, handlePrint, handleDelete, redirect, handleClone
        } = this.props;
        const {prompt} = this.state;

        return (
            <div className={"subheader-container overlay-shadow subheader-open js-not-unselect"}>
                <div className="container-fluid">
                    <div className="row">
                        <div className="subheader-row">
                            <div className=" subheader-column" >
                                {windowType && <div className="subheader-item" onClick={()=> redirect('/window/'+ windowType +'/new')}>
                                    <i className="meta-icon-report-1" /> New
                                </div>}
                                {dataId && <div className="subheader-item" onClick={()=> openModal(windowType, "window", "Advanced edit", true)}><i className="meta-icon-edit" /> Advanced Edit</div>}
                                {dataId && <div className="subheader-item" onClick={()=> handlePrint(windowType, dataId, docNo)}><i className="meta-icon-print" /> Print</div>}
                                {dataId && <div className="subheader-item" onClick={()=> handleClone(windowType, dataId)}><i className="meta-icon-duplicate" /> Clone</div>}
                                {dataId && <div className="subheader-item" onClick={()=> handleDelete()}><i className="meta-icon-delete" /> Delete</div>}
                                <div className="subheader-item" onClick={()=> redirect('/logout')}><i className="meta-icon-logout" /> Log out</div>
                            </div>
                            <div className=" subheader-column">
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
            </div>
        )
    }
}


Subheader.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Subheader = connect()(Subheader);

export default Subheader;
