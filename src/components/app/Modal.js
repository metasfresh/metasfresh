import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Window from '../Window';
import Process from '../Process';

import {
    closeModal,
    createWindow,
    createProcess,
    startProcess,
    handleProcessResponse
} from '../../actions/WindowActions';

class Modal extends Component {
    constructor(props) {
        super(props);

        const {
            rowId
        } = this.props;

        this.state = {
            scrolled: false,
            isNew: rowId === 'NEW',
            init: false,
            pending: false
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

        modalContent && modalContent.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        const modalContent = document.querySelector('.js-panel-modal-content')
        modalContent && modalContent.removeEventListener('scroll', this.handleScroll);
    }

    componentDidUpdate (prevProps) {
        const {
            windowType
        } = this.props;

        if(prevProps.windowType !== windowType){
            this.init();
        }
    }

    init = () => {
        const {
            dispatch, windowType, dataId, tabId, rowId, modalType, selected,
            relativeType, isAdvanced, modalViewId
        } = this.props;

        switch(modalType){
            case 'window':
                dispatch(
                    createWindow(windowType, dataId, tabId, rowId, true, isAdvanced)
                ).catch(() => {
                    this.handleClose();
                });
                break;
            case 'process':
                dispatch(
                    createProcess(
                        windowType, modalViewId, relativeType, dataId ? [dataId] : selected,
                        tabId, rowId
                    )
                ).catch(() => {
                    this.handleClose();
                });
                break;
        }
    }

    handleClose = () => {
        const {closeCallback} = this.props;
        const {isNew} = this.state;
        closeCallback && closeCallback(isNew);
        this.removeModal();
    }

    handleScroll = (event) => {
        const scrollTop = event.srcElement.scrollTop;

        this.setState({
            scrolled: scrollTop > 0
        })
    }

    handleStart = () => {
        this.setState(Object.assign({}, this.state, {
            pending: true
        }));

        const {dispatch, layout, windowType} = this.props;
        dispatch(startProcess(windowType, layout.pinstanceId)).then(response => {
            this.setState(Object.assign({}, this.state, {
                pending: false
            }));
            dispatch(handleProcessResponse(response, windowType, layout.pinstanceId, () => this.removeModal()));

        }).catch(() => {
            this.setState(Object.assign({}, this.state, {
                pending: false
            }));
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
            data, layout, tabId, rowId, dataId, modalType, windowType, isAdvanced
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
            data, modalTitle, modalType
        } = this.props;

        const {
            scrolled,
            pending
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
                                className={'btn btn-meta-outline-secondary btn-distance-3 btn-md ' + (pending ? 'tag-disabled disabled' : '')}
                                onClick={this.handleClose}
                                tabIndex={0}
                            >
                                {modalType === 'process' ? 'Cancel' : 'Done'}
                            </button>
                            {modalType === 'process' &&
                                <button
                                    className={'btn btn-meta-primary btn-distance-3 btn-md ' + (pending ? 'tag-disabled disabled' : '') }
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

Modal = connect()(Modal);

export default Modal
