import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Window from '../Window';
import Process from '../Process';

import {
    closeModal,
    createWindow,
    createProcess
} from '../../actions/WindowActions';

class Modal extends Component {
    constructor(props) {
        super(props);
        const {dispatch, windowType, dataId, tabId, rowId, modalType} = this.props;

        this.state = {
            scrolled: false,
            isAdvanced: false,
            isNew: rowId === "NEW",
            init: false
        }

        switch(modalType){
            case "window":
                dispatch(createWindow(windowType, dataId, tabId, rowId, true)).catch(err => {
                    this.handleClose();
                });
                break;
            case "process":
                dispatch(createProcess(windowType)).catch(err => {
                    this.handleClose();
                });
                break;
        }
    }

    isAdvancedEdit = () => {
        const {windowType} = this.props;
        let isAdvanceMode = (windowType.indexOf("advanced=true") != -1);

        this.setState(Object.assign({}, this.state, {
            isAdvanced: isAdvanceMode
        }));
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
        this.isAdvancedEdit();
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
        const {dispatch, closeCallback} = this.props;
        const {isNew} = this.state;
        dispatch(closeModal());

        if(isNew){
            closeCallback();
        }

        document.body.style.overflow = "auto";
    }

    render() {

        const {data, layout, modalTitle, tabId, rowId, dataId, modalType} = this.props;
        const {isAdvanced, scrolled} = this.state
        return (
            data.length > 0 && <div className="screen-freeze">
                <div className="panel panel-modal panel-modal-primary">
                    <div className={"panel-modal-header " + (scrolled ? "header-shadow": "")}>
                        <span className="panel-modal-header-title">{modalTitle ? modalTitle : "Modal"}</span>
                        <div className="items-row-2">
                            <span className="btn btn-meta-outline-secondary btn-distance-3 btn-md" onClick={this.handleClose}>
                                Done
                            </span>

                        </div>
                    </div>
                    <div className="panel-modal-content js-panel-modal-content container-fluid">
                        {modalType === "window" ?
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
                        :
                            <Process
                                data={data}
                                layout={layout}
                                type={windowType}
                            />
                        }
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
