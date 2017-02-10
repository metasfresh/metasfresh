import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import { push } from 'react-router-redux';

import Window from '../Window';
import Process from '../Process';
import DocumentList from './DocumentList';

import {
    closeModal,
    createWindow,
    createProcess,
    startProcess,
    handleProcessResponse
} from '../../actions/WindowActions';

import {
    addNotification
} from '../../actions/AppActions';

class Modal extends Component {
    constructor(props) {
        super(props);

        const {
            dispatch, windowType, dataId, tabId, rowId, modalType, viewId, selected,
            relativeType, isAdvanced
        } = this.props;

        this.state = {
            scrolled: false,
            isNew: rowId === "NEW",
            init: false
        }

        switch(modalType){
            case "window":
                dispatch(createWindow(windowType, dataId, tabId, rowId, true, isAdvanced)).catch(err => {
                    this.handleClose();
                });
                break;
            case "process":
                //processid, viewId, docType, id or ids
                dispatch(createProcess(windowType, viewId, relativeType, dataId ? dataId : selected)).catch(err => {
                    this.handleClose();
                });
                break;
            case "documentView":
                break;
        }
    }

    componentDidMount() {
        // Dirty solution, but use only if you need to
        // there is no way to affect body
        // because body is out of react app range
        // and css dont affect parents
        // but we have to change scope of scrollbar
        document.body.style.overflow = "hidden";

        const modalContent = document.querySelector('.js-panel-modal-content')

        modalContent && modalContent.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        const modalContent = document.querySelector('.js-panel-modal-content')
        modalContent && modalContent.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll = (event) => {
        let scrollTop = event.srcElement.scrollTop;

        if(scrollTop > 0) {
            this.setState(Object.assign({}, this.state, {
                scrolled: true
            }))
        } else {
            this.setState(Object.assign({}, this.state, {
                scrolled: false
            }))
        }
    }

    handleClose = () => {
        const {dispatch, closeCallback, modalType} = this.props;
        const {isNew} = this.state;

        closeCallback && closeCallback(isNew);
        this.removeModal();
    }

    handleStart = () => {
        const {dispatch, modalType, layout, windowType} = this.props;
        dispatch(startProcess(windowType, layout.pinstanceId)).then(response => {
            dispatch(handleProcessResponse(response, null, null, () => this.removeModal()));
        });
    }

    removeModal = () => {
        const {dispatch} = this.props;

        dispatch(closeModal());
        document.body.style.overflow = "auto";
    }

    renderModalBody = () => {
        const {
            data, layout, tabId, rowId, dataId, modalType, windowType,
            isAdvanced, modalViewId, query, selected
        } = this.props;

        switch(modalType){
            case "window":
                return (
                    <Window
                        data={data}
                        dataId={dataId}
                        layout={layout}
                        modal={true}
                        tabId={tabId}
                        rowId={rowId}
                        isModal={true}
                        isAdvanced={isAdvanced}
                    />
                )
            case "process":
                return (
                    <Process
                        data={data}
                        layout={layout}
                        type={windowType}
                    />
                )
        }
    }

    render() {
        const {
            data, layout, modalTitle, tabId, rowId, dataId, modalType, windowType,
            isAdvanced
        } = this.props;

        const {
            scrolled
        } = this.state;

        return (
            (modalType === "documentView" || data.length > 0) && <div
                className="screen-freeze js-not-unselect"
            >
                <div className="panel panel-modal panel-modal-primary">
                    <div
                        className={
                            "panel-modal-header " +
                            (scrolled ? "header-shadow": "")
                        }
                    >
                        <span className="panel-modal-header-title">
                            {modalTitle ? modalTitle : "Modal"}
                        </span>
                        <div className="items-row-2">
                            <button
                                className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
                                onClick={this.handleClose}
                                tabIndex={0}
                            >
                                {modalType === "process" ? "Cancel" : "Done"}
                            </button>
                            {modalType === "process" &&
                                <button
                                    className="btn btn-meta-primary btn-distance-3 btn-md"
                                    onClick={this.handleStart}
                                    tabIndex={0}
                                >
                                    Start
                                </button>
                            }
                        </div>
                    </div>
                    <div
                        className="panel-modal-content js-panel-modal-content container-fluid"
                        ref={c => { c && c.focus()}}
                    >
                        {this.renderModalBody()}
                    </div>
                </div>
            </div>
        )
    }
}

Modal.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Modal = connect()(Modal)

export default Modal
