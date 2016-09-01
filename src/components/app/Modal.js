import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Window from '../Window';

import {
    closeModal,
    createWindow
} from '../../actions/WindowActions';

class Modal extends Component {
    constructor(props) {
        super(props);

        const {dispatch, windowType, dataId} = this.props;
        dispatch(createWindow(windowType, "NEW", true));
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
        const {data, layout} = this.props;
        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-spaced panel-bordered panel-primary">
                    <div className="panel-modal-close" onClick={this.handleClose}>
                        <i className="meta-icon-close-1"/>
                    </div>
                    <Window
                        data={data}
                        layout={layout}
                        modal={true}
                    />
                </div>
            </div>
        )
    }
}

Modal.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    return {

    }
}

Modal = connect(mapStateToProps)(Modal)

export default Modal
