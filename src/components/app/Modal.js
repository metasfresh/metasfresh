import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import Window from '../Window';
import Process from '../Process';
import Indicator from './Indicator';
import OverlayField from './OverlayField';

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
    }

    componentDidMount() {
        this.init();

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
                )).catch(err => {
                    this.handleClose();
                    throw err;
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
                ).catch(err => {
                    this.handleClose();
                    if(err.toString() !== 'Error: close_modal'){
                        throw err;
                    }
                });
                break;
        }
    }

    handleClose = () => {
        const {
            modalSaveStatus, modalType
        } = this.props;

        if(modalType === 'process') {
            return this.closeModal();
        }

        if(modalSaveStatus || window.confirm('Do you really want to leave?')){
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
            processNewRecord(
                'window', windowType, dataId
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
        const target = event.srcElement;
        let scrollTop = target && target.body.scrollTop;

        if(!scrollTop){
            scrollTop = document.documentElement.scrollTop;
        }

        this.setState({
            scrolled: scrollTop > 0
        });
    }

    setFetchOnTrue = () => {
        this.setState({
            waitingFetch: true
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
            startProcess(
                windowType, layout.pinstanceId
            ).then(response => {
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
                        tabsInfo={null}
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

    renderPanel = () => {
        const {
            data, modalTitle, modalType, isDocumentNotSaved, layout
        } = this.props;

        const {
            scrolled, pending, isNewDoc
        } = this.state;

        return(
            Object.keys(data).length > 0 && <div
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
                        {modalTitle ? modalTitle : layout.caption}
                    </span>
                    <div className="items-row-2">
                        {isNewDoc && <button
                            className={
                                `btn btn-meta-outline-secondary
                                btn-distance-3 btn-md `+
                                (pending ? 'tag-disabled disabled ' : '')
                            }
                            onClick={this.removeModal}
                            tabIndex={0}
                        >
                            {counterpart.translate('modal.actions.cancel')}
                        </button>}
                        <button
                            className={
                                `btn btn-meta-outline-secondary
                                btn-distance-3 btn-md `+
                                (pending ? 'tag-disabled disabled ' : '')
                            }
                            onClick={this.handleClose}
                            tabIndex={0}
                        >
                            {modalType === 'process' ? 
                                counterpart.translate('modal.actions.cancel') : 
                                counterpart.translate('modal.actions.done')
                            }
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
                                {counterpart.translate('modal.actions.start')}
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

    renderOverlay = () => {
       const {
            data, layout, windowType
        } = this.props;

        const {pending} = this.state;

        return(
            <OverlayField
                type={windowType}
                disabled={pending}
                data={data}
                layout={layout}
                handleSubmit={this.setFetchOnTrue}
                closeOverlay={this.removeModal}
            />
        )
    }

    render() {
        const {
            layout
        } = this.props;

        return (
            <div>
                {
                    (!layout.layoutType || layout.layoutType === 'panel') &&
                    this.renderPanel()
                }
                {
                    layout.layoutType === 'singleOverlayField' &&
                    this.renderOverlay()
                }
            </div>
        )
    }
}

Modal.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Modal = connect()(Modal);

export default Modal
