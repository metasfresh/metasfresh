import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Window from '../Window';
import Process from '../Process';
import Indicator from './Indicator';

import {
    closeModal,
    createWindow,
    createProcess,
    startProcess,
    handleProcessResponse,
    patch
} from '../../actions/WindowActions';

import {
    processNewRecord
} from '../../actions/GenericActions';

class Modal extends Component {
    constructor(props) {
        super(props);

        const {
            rowId, dataId
        } = this.props;

        this.state = {
            scrolled: false,
            isNew: rowId === 'NEW',
            isNewDoc: dataId === 'NEW',
            init: false,
            pending: false,
            waitingFetch: false
        }

        this.init();
    }

    componentDidMount() {
        // Dirty solution, but use only if you need to
        // there is no way to affect body
        // because body is out of react app range
        // and css dont affect parents
        // but we have to change scope of scrollbar
        document.body.style.overflow = 'hidden';

        const modalContent = document.querySelector('.js-panel-modal-content')

        modalContent &&
            modalContent.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        const modalContent = document.querySelector('.js-panel-modal-content')
        modalContent &&
            modalContent.removeEventListener('scroll', this.handleScroll);
    }

    componentDidUpdate (prevProps) {
        const {
            windowType, indicator
        } = this.props;

        const {waitingFetch} = this.state;

        if(prevProps.windowType !== windowType){
            this.init();
        }

        // Case when we have to trigger pending start request
        // in due to some pending patches that are required.
        if(
            waitingFetch &&
            prevProps.indicator !== indicator
        ) {
            this.setState({
                waitingFetch: false
            }, () => {
                this.handleStart();
            })
        }
    }

    init = () => {
        const {
            dispatch, windowType, dataId, tabId, rowId, modalType, selected,
            relativeType, isAdvanced, modalViewId, modalViewDocumentIds
        } = this.props;

        switch(modalType){
            case 'window':
                dispatch(createWindow(
                    windowType, dataId, tabId, rowId, true, isAdvanced
                )).catch(() => {
                    this.handleClose();
                });
                break;
            case 'process':
                // We have 3 cases of processes (prioritized):
                // - with viewDocumentIds: on single page with rawModal
                // - with dataId: on single document page
                // - with selected : on gridviews
                dispatch(
                    createProcess(
                        windowType, modalViewId, relativeType,
                        modalViewDocumentIds || (dataId ? [dataId] : selected),
                        tabId, rowId
                    )
                ).catch(() => {
                    this.handleClose();
                });
                break;
        }
    }

    handleClose = () => {
        const {
            modalSaveStatus, modalType
        } = this.props;

        if(modalSaveStatus || modalType != 'process' && window.confirm('Do you really want to leave?')){
            this.closeModal();
        }
    }

    closeModal = () => {
        const {
            dispatch, closeCallback, dataId, windowType, relativeType,
            relativeDataId, triggerField
        } = this.props;
        const {isNew, isNewDoc} = this.state;

        if(isNewDoc) {
            dispatch(
                processNewRecord('window', windowType, dataId)
            ).then(response => {
                dispatch(patch(
                    'window', relativeType, relativeDataId, null, null,
                    triggerField, {[response.data]: ''}
                )).then(() => {
                    this.removeModal();
                })
            });
        }else{
            closeCallback && closeCallback(isNew);
            this.removeModal();
        }
    }

    handleScroll = (event) => {
        const scrollTop = event.srcElement.scrollTop;

        this.setState({
            scrolled: scrollTop > 0
        });
    }

    handleStart = () => {
        const {dispatch, layout, windowType, indicator} = this.props;

        if(indicator === 'pending'){
            this.setState({
                waitingFetch: true,
                pending: true
            });

            return;
        }

        this.setState({
            pending: true
        }, () => {
            dispatch(startProcess(
                windowType, layout.pinstanceId
            )).then(response => {
                this.setState({
                    pending: false
                }, () => {
                    dispatch(handleProcessResponse(
                        response, windowType, layout.pinstanceId,
                        () => this.removeModal()
                    ));
                });
            }).catch(() => {
                this.setState({
                    pending: false
                });
            });
        });
    }

    removeModal = () => {
        const {dispatch, rawModalVisible} = this.props;

        dispatch(closeModal());

        if (!rawModalVisible){
            document.body.style.overflow = 'auto';
        }
    }

    renderModalBody = () => {
        const {
            data, layout, tabId, rowId, dataId, modalType, windowType,
            isAdvanced
        } = this.props;

        const {pending} = this.state;

        switch(modalType){
            case 'window':
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
            case 'process':
                return (
                    <Process
                        data={data}
                        layout={layout}
                        type={windowType}
                        disabled={pending}
                    />
                )
        }
    }

    render() {
        const {
            data, modalTitle, modalType, isDocumentNotSaved
        } = this.props;

        const {
            scrolled, pending
        } = this.state;

        return (
            data.length > 0 && <div
                className="screen-freeze js-not-unselect"
            >
                <div className="panel panel-modal panel-modal-primary">
                    <div
                        className={
                            'panel-modal-header ' +
                            (scrolled ? 'header-shadow': '')
                        }
                    >
                        <span className="panel-modal-header-title">
                            {modalTitle ? modalTitle : 'Modal'}
                        </span>
                        <div className="items-row-2">
                            <button
                                className={
                                    `btn btn-meta-outline-secondary
                                    btn-distance-3 btn-md `+
                                    (pending ? 'tag-disabled disabled ' : '')
                                }
                                onClick={this.handleClose}
                                tabIndex={0}
                            >
                                {modalType === 'process' ? 'Cancel' : 'Done'}
                            </button>
                            {modalType === 'process' &&
                                <button
                                    className={
                                        `btn btn-meta-primary btn-distance-3
                                        btn-md ` +
                                        (pending ? 'tag-disabled disabled' : '')
                                    }
                                    onClick={this.handleStart}
                                    tabIndex={0}
                                >
                                    Start
                                </button>
                            }
                        </div>
                    </div>
                    <Indicator {...{isDocumentNotSaved}}/>
                    <div
                        className={
                            `panel-modal-content js-panel-modal-content
                            container-fluid`
                        }
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

Modal = connect()(Modal);

export default Modal
