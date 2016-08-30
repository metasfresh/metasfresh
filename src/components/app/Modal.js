import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    closeModal
} from '../../actions/WindowActions';

class Modal extends Component {
    constructor(props) {
        super(props);
    }
    handleClose = () => {
        this.props.dispatch(closeModal());
    }
    render() {
        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-spaced panel-bordered panel-primary">
                    <div className="panel-modal-close" onClick={this.handleClose}>
                        <i className="meta-icon-close-1"/>
                    </div>
                    Modal
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
