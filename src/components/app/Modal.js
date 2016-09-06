import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Window from '../Window';
import Indicator from './Indicator';

import {
    closeModal,
    createWindow
} from '../../actions/WindowActions';

class Modal extends Component {
    constructor(props) {
        super(props);

        const {dispatch, windowType, dataId, tabId, rowId} = this.props;
        dispatch(createWindow(windowType, dataId, tabId, rowId, true));
    }

    componentDidMount() {
        // Dirty solution, but use only if you need to
        // there is no way to affect body
        // because body is out of react app range
        // and css dont affect parents
        // but we have to change scope of scrollbar
        document.body.style.overflow = "hidden";
    }
    handleClose = () => {
        this.props.dispatch(closeModal());

        document.body.style.overflow = "auto";
    }
    render() {
        const {data, layout, indicator, modalTitle, tabId, rowId, dataId} = this.props;
        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-modal-primary">
                    <div className="panel-modal-header">
                        <span className="panel-modal-header-title">{modalTitle ? modalTitle : "Modal"}</span>
                        <div className="items-row-2">
                            <span className="btn btn-meta-outline-secondary btn-distance-3 btn-md" onClick={this.handleClose}>
                                Done
                            </span>
                            <Indicator indicator={indicator} />
                        </div>
                    </div>
                    <div className="panel-modal-content">
                        <Window
                            data={data}
                            dataId={dataId}
                            layout={layout}
                            modal={true}
                            tabId={tabId}
                            rowId={rowId}
                            isModal={true}
                        />
                    </div>
                </div>
            </div>
        )
    }
}

Modal.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { indicator } = state.windowHandler;
    return {
        indicator
    }
}

Modal = connect(mapStateToProps)(Modal)

export default Modal
